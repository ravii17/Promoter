const crypto = require("crypto");
const express = require("express");
const { z } = require("zod");
const pool = require("../db");
const { redis, ensureRedisConnected, sessionKey } = require("../redis");

const router = express.Router();

const OTP_TTL_SECONDS = 60;
const SESSION_TTL_SECONDS = 7 * 24 * 60 * 60;
const PENDING_ROLE = "pending_role_selection";

const recipientSchema = z.object({
  recipient: z.string().trim().min(5).max(120),
});

const verifyOtpSchema = recipientSchema.extend({
  otp: z.string().trim().regex(/^\d{4}$/),
});

function isEmail(value) {
  return value.includes("@");
}

function normalizeRecipient(value) {
  return value.trim();
}

function generateOtp() {
  return String(crypto.randomInt(0, 10_000)).padStart(4, "0");
}

function generateSessionToken() {
  return crypto.randomUUID();
}

async function sendOtpViaMsg91(phone, otp) {
  const authKey = process.env.MSG91_AUTH_KEY;
  const templateId = process.env.MSG91_TEMPLATE_ID;

  if (!authKey || !templateId) {
    throw new Error("MSG91_AUTH_KEY and MSG91_TEMPLATE_ID are required");
  }

  const url = new URL("https://control.msg91.com/api/v5/otp");
  url.searchParams.set("template_id", templateId);
  url.searchParams.set("mobile", phone);
  url.searchParams.set("otp", otp);

  if (process.env.MSG91_SENDER_ID) {
    url.searchParams.set("sender", process.env.MSG91_SENDER_ID);
  }

  if (process.env.MSG91_OTP_EXPIRY_MINUTES) {
    url.searchParams.set("otp_expiry", process.env.MSG91_OTP_EXPIRY_MINUTES);
  }

  const response = await fetch(url, {
    method: "GET",
    headers: {
      authkey: authKey,
      accept: "application/json",
    },
  });

  if (!response.ok) {
    const message = await response.text();
    throw new Error(`MSG91 OTP send failed: ${response.status} ${message}`);
  }
}

async function storeOtp(recipient, code) {
  await pool.query("DELETE FROM otps WHERE recipient = $1", [recipient]);
  await pool.query(
    `
      INSERT INTO otps (recipient, code, expires_at)
      VALUES ($1, $2, NOW() + ($3 || ' seconds')::interval)
    `,
    [recipient, code, String(OTP_TTL_SECONDS)],
  );
}

async function validateOtp(recipient, code) {
  const result = await pool.query(
    `
      SELECT id
      FROM otps
      WHERE recipient = $1
        AND code = $2
        AND expires_at > NOW()
      ORDER BY created_at DESC
      LIMIT 1
    `,
    [recipient, code],
  );

  if (!result.rows[0]) {
    return false;
  }

  await pool.query("DELETE FROM otps WHERE recipient = $1", [recipient]);
  return true;
}

async function findUserByRecipient(recipient) {
  if (isEmail(recipient)) {
    const result = await pool.query(
      `
        SELECT user_id, phone, email, name, city, role, kyc_status, profile_photo_url
        FROM users
        WHERE email = $1
        LIMIT 1
      `,
      [recipient.toLowerCase()],
    );
    return result.rows[0] || null;
  }

  const result = await pool.query(
    `
      SELECT user_id, phone, email, name, city, role, kyc_status, profile_photo_url
      FROM users
      WHERE phone = $1
      LIMIT 1
    `,
    [recipient],
  );
  return result.rows[0] || null;
}

async function createUser(recipient) {
  if (isEmail(recipient)) {
    const result = await pool.query(
      `
        INSERT INTO users (email, role, kyc_status)
        VALUES ($1, $2, 'pending')
        RETURNING user_id, phone, email, name, city, role, kyc_status, profile_photo_url
      `,
      [recipient.toLowerCase(), PENDING_ROLE],
    );
    return result.rows[0];
  }

  const result = await pool.query(
    `
      INSERT INTO users (phone, role, kyc_status)
      VALUES ($1, $2, 'pending')
      RETURNING user_id, phone, email, name, city, role, kyc_status, profile_photo_url
    `,
    [recipient, PENDING_ROLE],
  );
  return result.rows[0];
}

function mapUserResponse(user) {
  return {
    user_id: user.user_id,
    phone: user.phone,
    email: user.email,
    name: user.name || "",
    city: user.city || "",
    role: user.role,
    kyc_status: user.kyc_status,
    profile_photo_url: user.profile_photo_url || "",
    profile_complete: Boolean(user.name && (user.phone || user.email) && user.city),
  };
}

function sendValidationError(res, error) {
  return res.status(400).json({
    error: "Invalid request",
    details: error.issues.map((issue) => ({
      path: issue.path.join("."),
      message: issue.message,
    })),
  });
}

async function handleSendOtp(req, res) {
  const parsed = recipientSchema.safeParse(req.body);
  if (!parsed.success) {
    return sendValidationError(res, parsed.error);
  }

  const recipient = normalizeRecipient(parsed.data.recipient);
  const otp = generateOtp();

  try {
    await storeOtp(recipient, otp);

    if (process.env.DEV_MODE === "true") {
      console.log(`[DEV] OTP for ${recipient}: ${otp}`);
    } else if (!isEmail(recipient)) {
      await sendOtpViaMsg91(recipient, otp);
    } else {
      console.log(`[EMAIL OTP] ${recipient}: ${otp}`);
    }

    return res.json({ success: true, message: "OTP sent" });
  } catch (error) {
    console.error("send-otp failed:", error.message);
    return res.status(500).json({ error: "Unable to send OTP" });
  }
}

async function handleVerifyOtp(req, res) {
  const parsed = verifyOtpSchema.safeParse(req.body);
  if (!parsed.success) {
    return sendValidationError(res, parsed.error);
  }

  const recipient = normalizeRecipient(parsed.data.recipient);
  const { otp } = parsed.data;

  try {
    const valid = await validateOtp(recipient, otp);
    if (!valid) {
      return res.status(401).json({ error: "Invalid or expired OTP" });
    }

    let user = await findUserByRecipient(recipient);
    const isNewUser = !user;

    if (!user) {
      user = await createUser(recipient);
    }

    await ensureRedisConnected();
    const token = generateSessionToken();
    await redis.set(
      sessionKey(token),
      JSON.stringify({
        user_id: user.user_id,
        role: user.role,
      }),
      { EX: SESSION_TTL_SECONDS },
    );

    return res.json({
      token,
      is_new_user: isNewUser,
      ...mapUserResponse(user),
    });
  } catch (error) {
    console.error("verify-otp failed:", error.message);
    return res.status(500).json({ error: "Unable to verify OTP" });
  }
}

router.post("/otp", handleSendOtp);
router.post("/verify-otp", handleVerifyOtp);

// Legacy organiser paths (phone-only body)
router.post("/organiser/send-otp", async (req, res) => {
  req.body = { recipient: req.body?.phone };
  return handleSendOtp(req, res);
});

router.post("/organiser/verify-otp", async (req, res) => {
  req.body = { recipient: req.body?.phone, otp: req.body?.otp };
  return handleVerifyOtp(req, res);
});

module.exports = router;
