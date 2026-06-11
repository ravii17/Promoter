process.on('unhandledRejection', (reason) => {
  console.error('UNHANDLED REJECTION:', reason);
});
process.on('uncaughtException', (err) => {
  console.error('UNCAUGHT EXCEPTION:', err);
});

require("dotenv").config();
console.log("DB CONFIG:", { user: process.env.PG_USER, password: process.env.PG_PASSWORD, db: process.env.PG_DATABASE, host: process.env.PG_HOST, port: process.env.PG_PORT });
const express = require("express");

const authRouter = require("./routes/auth");
const registerRouter = require("./routes/register");

const app = express();
const PORT = process.env.PORT || 3000;

app.use(express.json());

app.get("/health", (req, res) => {
  res.status(200).json({ status: "ok" });
});

app.use("/auth/organiser", authRouter);
app.use("/auth/organiser", registerRouter);

app.use((req, res) => {
  res.status(404).json({ error: "Route not found" });
});

app.listen(PORT, () => {
  console.log(`Promotr Organiser Auth API running on port ${PORT}`);
});