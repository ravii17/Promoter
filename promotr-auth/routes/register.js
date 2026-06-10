const express = require("express");
// const axios = require("axios");
const { v4: uuidv4 } = require("uuid");
const pool = require("../db");
// const redis = require("../redis");

const router = express.Router();

const OTP_TTL_SECONDS = 60;
const VERIFIED_TTL_SECONDS = 600;
const SESSION_TTL_SECONDS = 604800;
const ORGANISER_ROLE = "organiser";
const PENDING_KYC_STATUS = "pending";

function generateOtp() {
  return Math.floor(100000 + Math.random() * 900000).toString();
}

function registrationOtpKey(phone) {
  return `reg_otp:${phone}`;
}

function registrationVerifiedKey(phone) {
  return `reg_verified:${phone}`;
}

function sessionKey(token) {
  return `session:${token}`;
}

async function sendOtpViaMsg91(phone, otp) {
  // await axios.post(
  //   "https://control.msg91.com/api/v5/otp",
  //   {
  //     template_id: process.env.MSG91_TEMPLATE_ID,
  //     mobile: phone,
  //     otp,
  //   },
  //   {
  //     headers: {
  //       authkey: process.env.MSG91_API_KEY,
  //       "Content-Type": "application/json",
  //     },
  //     timeout: 10000,
  //   },
  // );
}

router.post("/register/send-otp", async (req, res) => {
  const { phone } = req.body || {};

  if (!phone) {
    return res.status(400).json({ error: "Phone is required" });
  }

  try {
    const existingUser = await pool.query(
      "SELECT user_id FROM users WHERE phone = $1",
      [phone],
    );

    if (existingUser.rows.length > 0) {
      return res.status(409).json({ error: "Phone already registered" });
    }

    const otp = "123456";
    const key = registrationOtpKey(phone);

    // await redis.set(key, otp, "EX", OTP_TTL_SECONDS);

    // try {
    //   await sendOtpViaMsg91(phone, otp);
    // } catch (error) {
    //   await redis.del(key);
    //   throw error;
    // }

    return res.status(200).json({
      success: true,
      otp,
    });
  } catch (error) {
    console.error(error);
    return res.status(500).json({ error: "Internal server error" });
  }
});

router.post("/register/verify-otp", async (req, res) => {
  const { phone, otp } = req.body || {};

  if (!phone || !otp) {
    return res.status(400).json({ error: "Phone and OTP are required" });
  }

  try {
    const key = registrationOtpKey(phone);
    // const storedOtp = await redis.get(key);
    const storedOtp = "123456";

    if (!storedOtp) {
      return res.status(401).json({ error: "OTP expired" });
    }

    if (storedOtp !== otp) {
      return res.status(401).json({ error: "Invalid OTP" });
    }

    // await redis.del(key);
    // await redis.set(registrationVerifiedKey(phone), "true", "EX", VERIFIED_TTL_SECONDS);

    return res.status(200).json({
      success: true,
      message: "Phone verified",
    });
  } catch (error) {
    console.error(error);
    return res.status(500).json({ error: "Internal server error" });
  }
});

router.post("/register/complete", async (req, res) => {
  const { phone, name, email } = req.body || {};

  if (!phone || !name) {
    return res.status(400).json({ error: "Phone and name are required" });
  }

  try {
    const verifiedKey = registrationVerifiedKey(phone);
    // const isVerified = await redis.get(verifiedKey);
    const isVerified = "true";

    if (!isVerified) {
      return res.status(403).json({ error: "Phone not verified" });
    }

    const insertedUser = await pool.query(
      `
        INSERT INTO users (phone, name, email, role, kyc_status)
        VALUES ($1, $2, $3, $4, $5)
        RETURNING user_id, name, phone, role, kyc_status
      `,
      [phone, name, email || null, ORGANISER_ROLE, PENDING_KYC_STATUS],
    );

    // await redis.del(verifiedKey);

    const user = insertedUser.rows[0];
    const token = uuidv4();

    // await redis.set(
    //   sessionKey(token),
    //   JSON.stringify({
    //     user_id: user.user_id,
    //     role: ORGANISER_ROLE,
    //   }),
    //   "EX",
    //   SESSION_TTL_SECONDS,
    // );

    return res.status(200).json({
      token,
      user_id: user.user_id,
      name: user.name,
      phone: user.phone,
      role: user.role,
      kyc_status: user.kyc_status,
    });
  } catch (error) {
    if (error.code === "23505") {
      return res.status(409).json({ error: "Phone already registered" });
    }

    console.error(error);
    return res.status(500).json({ error: "Internal server error" });
  }
});

module.exports = router;
