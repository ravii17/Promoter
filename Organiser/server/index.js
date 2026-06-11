require("dotenv").config();

const path = require("path");
const express = require("express");
const cors = require("cors");
const authRoutes = require("./routes/auth");
const userRoutes = require("./routes/users");
const { redis } = require("./redis");

const app = express();
const port = Number(process.env.PORT || 4000);

app.use(cors());
app.use(express.json());
app.use("/uploads", express.static(path.join(__dirname, "uploads")));

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
      "POST /auth/otp",
      "POST /auth/verify-otp",
      "PATCH /users/role",
      "POST /users/profile",
      "POST /users/profile/photo",
      "GET /users/me",
      "GET /health",
    ],
  });
});

app.use("/auth", authRoutes);
app.use("/users", userRoutes);

async function start() {
  app.listen(port, () => {
    console.log(`Promotr organiser auth API listening on port ${port}`);
  });
}

start().catch((error) => {
  console.error("Server startup failed:", error.message);
  process.exit(1);
});
