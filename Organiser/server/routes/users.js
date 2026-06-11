const express = require("express");
const fs = require("fs");
const path = require("path");
const multer = require("multer");
const { z } = require("zod");
const pool = require("../db");
const { requireAuth } = require("../middleware/auth");

const router = express.Router();

const VALID_ROLES = new Set(["crew", "organiser", "buyer"]);

const roleSchema = z.object({
  role: z.enum(["crew", "organiser", "buyer"]),
});

const profileSchema = z.object({
  name: z.string().trim().min(2).max(120),
  email: z.string().trim().email().optional().or(z.literal("")),
  city: z.string().trim().min(2).max(120),
  profile_photo_url: z.string().trim().url().optional().or(z.literal("")),
});

const uploadsDir = path.join(__dirname, "..", "uploads");
if (!fs.existsSync(uploadsDir)) {
  fs.mkdirSync(uploadsDir, { recursive: true });
}

const upload = multer({
  storage: multer.diskStorage({
    destination: uploadsDir,
    filename: (_req, file, cb) => {
      const ext = path.extname(file.originalname) || ".jpg";
      cb(null, `${Date.now()}-${Math.round(Math.random() * 1e9)}${ext}`);
    },
  }),
  limits: { fileSize: 5 * 1024 * 1024 },
});

function sendValidationError(res, error) {
  return res.status(400).json({
    error: "Invalid request",
    details: error.issues.map((issue) => ({
      path: issue.path.join("."),
      message: issue.message,
    })),
  });
}

async function getUserById(userId) {
  const result = await pool.query(
    `
      SELECT user_id, phone, email, name, city, role, kyc_status, profile_photo_url
      FROM users
      WHERE user_id = $1
      LIMIT 1
    `,
    [userId],
  );
  return result.rows[0] || null;
}

router.get("/me", requireAuth, async (req, res) => {
  try {
    const user = await getUserById(req.auth.user_id);
    if (!user) {
      return res.status(404).json({ error: "User not found" });
    }

    return res.json({
      user_id: user.user_id,
      phone: user.phone,
      email: user.email,
      name: user.name || "",
      city: user.city || "",
      role: user.role,
      kyc_status: user.kyc_status,
      profile_photo_url: user.profile_photo_url || "",
      profile_complete: Boolean(user.name && user.city),
    });
  } catch (error) {
    console.error("GET /users/me failed:", error.message);
    return res.status(500).json({ error: "Unable to fetch profile" });
  }
});

router.patch("/role", requireAuth, async (req, res) => {
  const parsed = roleSchema.safeParse(req.body);
  if (!parsed.success) {
    return sendValidationError(res, parsed.error);
  }

  const { role } = parsed.data;
  if (!VALID_ROLES.has(role)) {
    return res.status(400).json({ error: "Invalid role" });
  }

  try {
    const result = await pool.query(
      `
        UPDATE users
        SET role = $1, updated_at = NOW()
        WHERE user_id = $2
        RETURNING user_id, phone, email, name, city, role, kyc_status, profile_photo_url
      `,
      [role, req.auth.user_id],
    );

    if (!result.rows[0]) {
      return res.status(404).json({ error: "User not found" });
    }

    const user = result.rows[0];
    return res.json({
      success: true,
      user_id: user.user_id,
      role: user.role,
      name: user.name || "",
      city: user.city || "",
      kyc_status: user.kyc_status,
      profile_photo_url: user.profile_photo_url || "",
    });
  } catch (error) {
    console.error("PATCH /users/role failed:", error.message);
    return res.status(500).json({ error: "Unable to update role" });
  }
});

router.post("/profile/photo", requireAuth, upload.single("photo"), (req, res) => {
  if (!req.file) {
    return res.status(400).json({ error: "Photo file is required" });
  }

  const baseUrl = process.env.PUBLIC_BASE_URL || `http://localhost:${process.env.PORT || 4000}`;
  const url = `${baseUrl}/uploads/${req.file.filename}`;
  return res.json({ success: true, profile_photo_url: url });
});

router.post("/profile", requireAuth, async (req, res) => {
  const parsed = profileSchema.safeParse(req.body);
  if (!parsed.success) {
    return sendValidationError(res, parsed.error);
  }

  const { name, city, profile_photo_url } = parsed.data;
  const email = parsed.data.email || null;

  try {
    const result = await pool.query(
      `
        UPDATE users
        SET
          name = $1,
          email = COALESCE(NULLIF($2, ''), email),
          city = $3,
          profile_photo_url = COALESCE(NULLIF($4, ''), profile_photo_url),
          updated_at = NOW()
        WHERE user_id = $5
        RETURNING user_id, phone, email, name, city, role, kyc_status, profile_photo_url
      `,
      [name, email, city, profile_photo_url || "", req.auth.user_id],
    );

    if (!result.rows[0]) {
      return res.status(404).json({ error: "User not found" });
    }

    const user = result.rows[0];
    return res.json({
      success: true,
      user_id: user.user_id,
      phone: user.phone,
      email: user.email,
      name: user.name,
      city: user.city,
      role: user.role,
      kyc_status: user.kyc_status,
      profile_photo_url: user.profile_photo_url || "",
    });
  } catch (error) {
    if (error.code === "23505") {
      return res.status(409).json({ error: "Email already in use" });
    }

    console.error("POST /users/profile failed:", error.message);
    return res.status(500).json({ error: "Unable to save profile" });
  }
});

module.exports = router;
