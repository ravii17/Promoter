const Redis = require("ioredis");

let redis;

try {
  redis = new Redis(process.env.REDIS_URL || "redis://localhost:6379", {
    lazyConnect: true,
    maxRetriesPerRequest: 1,
  });

  redis.on("error", (error) => {
    console.warn("Redis warning:", error.message);
  });
} catch (error) {
  console.warn("Redis warning:", error.message);
}

module.exports = redis;
