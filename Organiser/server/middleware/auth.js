const { redis, ensureRedisConnected, sessionKey } = require("../redis");

async function requireAuth(req, res, next) {
  const header = req.headers.authorization || "";
  const token = header.startsWith("Bearer ") ? header.slice(7) : null;

  if (!token) {
    return res.status(401).json({ error: "Authorization token required" });
  }

  try {
    await ensureRedisConnected();
    const raw = await redis.get(sessionKey(token));
    if (!raw) {
      return res.status(401).json({ error: "Session expired" });
    }

    req.auth = { token, ...JSON.parse(raw) };
    next();
  } catch (error) {
    console.error("auth middleware failed:", error.message);
    return res.status(503).json({ error: "Auth service unavailable" });
  }
}

module.exports = { requireAuth };
