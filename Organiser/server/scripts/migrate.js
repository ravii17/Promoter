require("dotenv").config({ path: require("path").join(__dirname, "..", ".env") });

const fs = require("fs");
const path = require("path");
const { Pool } = require("pg");

async function migrate() {
  const connectionString = process.env.DATABASE_URL;
  if (!connectionString) {
    console.error("DATABASE_URL is required");
    process.exit(1);
  }

  const pool = new Pool({ connectionString });
  const sqlPath = path.join(__dirname, "..", "migrations", "001_initial_schema.sql");
  const sql = fs.readFileSync(sqlPath, "utf8");

  try {
    await pool.query(sql);
    console.log("Migration completed successfully.");
  } catch (error) {
    console.error("Migration failed:", error.message);
    process.exit(1);
  } finally {
    await pool.end();
  }
}

migrate();
