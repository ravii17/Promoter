require("dotenv").config();

const crypto = require("crypto");
const express = require("express");
const cors = require("cors");
const { Pool } = require("pg");
const { createClient } = require("redis");
const { z } = require("zod");

const app = express();
const port = Number(process.env.PORT || 4000);

const OTP_TTL_SECONDS = 60;
const SESSION_TTL_SECONDS = 7 * 24 * 60 * 60;
const ORGANISER_ROLE = "organiser";

const pool = new Pool({
  connectionString: process.env.DATABASE_URL,
});

const redis = createClient({
  url: process.env.REDIS_URL || "redis://localhost:6379",
  socket: {
    reconnectStrategy: false,
  },
});

redis.on("error", (error) => {
  console.error("Redis error:", error.message);
});

app.use(cors());
app.use(express.json());

const phoneSchema = z.object({
  phone: z.string().trim().min(5).max(20),
});

const verifyOtpSchema = phoneSchema.extend({
  otp: z.string().trim().regex(/^\d{6}$/),
});

function otpKey(phone) {
  return `otp:${phone}`;
}

function sessionKey(token) {
  return `session:${token}`;
}

function generateOtp() {
  return String(crypto.randomInt(0, 1_000_000)).padStart(6, "0");
}

function generateSessionToken() {
  return crypto.randomUUID();
}

async function ensureRedisConnected() {
  if (redis.isOpen) {
    return;
  }

  await redis.connect();
}

async function findOrganiserByPhone(phone) {
  const result = await pool.query(
    `
      SELECT user_id, name, kyc_status, role
      FROM users
      WHERE phone = $1 AND role = $2
      LIMIT 1
    `,
    [phone, ORGANISER_ROLE],
  );

  return result.rows[0] || null;
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

function sendValidationError(res, error) {
  return res.status(400).json({
    error: "Invalid request",
    details: error.issues.map((issue) => ({
      path: issue.path.join("."),
      message: issue.message,
    })),
  });
}

app.get("/health", (_req, res) => {
  res.json({
    success: true,
    redis: redis.isOpen ? "connected" : "not connected",
  });
});

app.get("/", (_req, res) => {
  res.json({
    success: true,
    service: "Promotr organiser auth API",
    endpoints: [
      "POST /auth/organiser/send-otp",
      "POST /auth/organiser/verify-otp",
      "GET /health",
    ],
  });
});

app.post("/auth/organiser/send-otp", async (req, res) => {
  const parsed = phoneSchema.safeParse(req.body);
  if (!parsed.success) {
    return sendValidationError(res, parsed.error);
  }

  const { phone } = parsed.data;

  try {
    await ensureRedisConnected();

    const organiser = await findOrganiserByPhone(phone);
    if (!organiser) {
      return res.status(403).json({ error: "Phone is not registered as organiser" });
    }

    const otp = generateOtp();
    await redis.set(otpKey(phone), otp, { EX: OTP_TTL_SECONDS });

    try {
      await sendOtpViaMsg91(phone, otp);
    } catch (error) {
      await redis.del(otpKey(phone));
      throw error;
    }

    return res.json({ success: true, message: "OTP sent" });
  } catch (error) {
    console.error("send-otp failed:", error.message);
    if (error.message.includes("ECONNREFUSED")) {
      return res.status(503).json({ error: "Redis unavailable" });
    }

    return res.status(500).json({ error: "Unable to send OTP" });
  }
});

app.post("/auth/organiser/verify-otp", async (req, res) => {
  const parsed = verifyOtpSchema.safeParse(req.body);
  if (!parsed.success) {
    return sendValidationError(res, parsed.error);
  }

  const { phone, otp } = parsed.data;

  try {
    await ensureRedisConnected();

    const storedOtp = await redis.get(otpKey(phone));
    if (!storedOtp) {
      return res.status(400).json({ error: "OTP expired" });
    }

    if (storedOtp !== otp) {
      return res.status(401).json({ error: "Invalid OTP" });
    }

    await redis.del(otpKey(phone));

    const organiser = await findOrganiserByPhone(phone);
    if (!organiser) {
      return res.status(403).json({ error: "Phone is not registered as organiser" });
    }

    const token = generateSessionToken();
    await redis.set(
      sessionKey(token),
      JSON.stringify({
        user_id: organiser.user_id,
        role: ORGANISER_ROLE,
      }),
      { EX: SESSION_TTL_SECONDS },
    );

    return res.json({
      token,
      user_id: organiser.user_id,
      name: organiser.name,
      kyc_status: organiser.kyc_status,
    });
  } catch (error) {
    console.error("verify-otp failed:", error.message);
    if (error.message.includes("ECONNREFUSED")) {
      return res.status(503).json({ error: "Redis unavailable" });
    }

    return res.status(500).json({ error: "Unable to verify OTP" });
  }
});

async function start() {
  app.listen(port, () => {
    console.log(`Promotr organiser auth API listening on port ${port}`);
  });
}

start().catch((error) => {
  console.error("Server startup failed:", error.message);
  process.exit(1);
});
