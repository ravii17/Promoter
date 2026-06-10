const express = require("express");
const axios = require("axios");
const { v4: uuidv4 } = require("uuid");
const pool = require("../db");
const redis = require("../redis");

const router = express.Router();

const OTP_TTL_SECONDS = 60;
const SESSION_TTL_SECONDS = 604800;
const ORGANISER_ROLE = "organiser";

function generateOtp() {
  return Math.floor(100000 + Math.random() * 900000).toString();
}

function otpKey(phone) {
  return `otp:${phone}`;
}

function sessionKey(token) {
  return `session:${token}`;
}

async function sendOtpViaMsg91(phone, otp) {
  await axios.post(
    "https://control.msg91.com/api/v5/otp",
    {
      template_id: process.env.MSG91_TEMPLATE_ID,
      mobile: phone,
      otp,
    },
    {
      headers: {
        authkey: process.env.MSG91_API_KEY,
        "Content-Type": "application/json",
      },
      timeout: 10000,
    },
  );
}

router.post("/send-otp", async (req, res) => {
  const { phone } = req.body || {};

  if (!phone) {
    return res.status(400).json({ error: "Phone is required" });
  }

  try {
    const userResult = await pool.query(
      "SELECT user_id, role FROM users WHERE phone = $1",
      [phone],
    );

    if (userResult.rows.length === 0) {
      return res.status(403).json({ error: "Phone not registered" });
    }

    const user = userResult.rows[0];

    if (user.role !== ORGANISER_ROLE) {
      return res.status(403).json({ error: "Not an organiser account" });
    }

    const otp = generateOtp();
    await redis.set(otpKey(phone), otp, "EX", OTP_TTL_SECONDS);

    try {
      await sendOtpViaMsg91(phone, otp);
    } catch (error) {
      await redis.del(otpKey(phone));
      throw error;
    }

    return res.status(200).json({
      success: true,
      message: "OTP sent",
    });
  } catch (error) {
    console.error(error);
    return res.status(500).json({ error: "Internal server error" });
  }
});

router.post("/verify-otp", async (req, res) => {
  const { phone, otp } = req.body || {};

  if (!phone || !otp) {
    return res.status(400).json({ error: "Phone and OTP are required" });
  }

  try {
    const key = otpKey(phone);
    const storedOtp = await redis.get(key);

    if (!storedOtp) {
      return res.status(401).json({ error: "OTP expired" });
    }

    if (storedOtp !== otp) {
      return res.status(401).json({ error: "Invalid OTP" });
    }

    await redis.del(key);

    const userResult = await pool.query(
      "SELECT user_id, name, role, kyc_status FROM users WHERE phone = $1 AND role = 'organiser'",
      [phone],
    );

    if (userResult.rows.length === 0) {
      return res.status(403).json({ error: "Not an organiser account" });
    }

    const user = userResult.rows[0];
    const token = uuidv4();

    await redis.set(
      sessionKey(token),
      JSON.stringify({
        user_id: user.user_id,
        role: user.role,
      }),
      "EX",
      SESSION_TTL_SECONDS,
    );

    return res.status(200).json({
      token,
      user_id: user.user_id,
      name: user.name,
      role: user.role,
      kyc_status: user.kyc_status,
    });
  } catch (error) {
    console.error(error);
    return res.status(500).json({ error: "Internal server error" });
  }
});

module.exports = router;
