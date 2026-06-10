require("dotenv").config();

const express = require("express");
const path = require("path");
const authRouter = require("./routes/auth");
const registerRouter = require("./routes/register");

const app = express();
const PORT = process.env.PORT || 3000;

app.use(express.json());
app.use(express.static(path.join(__dirname, "public")));

app.get("/health", (req, res) => {
  res.status(200).json({ status: "ok" });
});

app.use("/auth/organiser", authRouter);
app.use("/auth/organiser", registerRouter);

app.use((req, res) => {
  res.status(404).json({ error: "Route not found" });
});

app.use((err, req, res, next) => {
  console.error(err);
  res.status(500).json({ error: "Internal server error" });
});

app.listen(PORT, () => {
  console.log(`Promotr Organiser Auth API running on port ${PORT}`);
});
