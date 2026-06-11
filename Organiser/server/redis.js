const { createClient } = require("redis");

const redis = createClient({
  url: process.env.REDIS_URL || "redis://localhost:6379",
  socket: {
    reconnectStrategy: false,
  },
});

redis.on("error", (error) => {
  console.error("Redis error:", error.message);
});

async function ensureRedisConnected() {
  if (!redis.isOpen) {
    await redis.connect();
  }
}

function sessionKey(token) {
  return `session:${token}`;
}

module.exports = { redis, ensureRedisConnected, sessionKey };
