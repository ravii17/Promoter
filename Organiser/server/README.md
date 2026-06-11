# Promotr Organiser Auth API

PostgreSQL-backed OTP login, role selection, and profile setup for the Organiser Android app.

## Prerequisites

- Node.js 18+
- PostgreSQL 14+
- Redis (for session tokens)

## Setup

```bash
cd Organiser/server
cp .env.example .env
npm install
npm run migrate
npm run dev
```

The API runs on `http://localhost:4000`.

## Environment

| Variable | Description |
|----------|-------------|
| `DATABASE_URL` | PostgreSQL connection string |
| `REDIS_URL` | Redis URL for sessions |
| `DEV_MODE=true` | Logs OTP to console (skips MSG91) |
| `PUBLIC_BASE_URL` | Base URL for uploaded profile photos |

## API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| POST | `/auth/otp` | Send 4-digit OTP (`{ "recipient": "phone or email" }`) |
| POST | `/auth/verify-otp` | Verify OTP, returns token + `is_new_user` |
| PATCH | `/users/role` | Set role (`organiser`, `crew`, `buyer`) — requires Bearer token |
| POST | `/users/profile` | Save name, email, city, photo URL — requires Bearer token |
| POST | `/users/profile/photo` | Upload profile photo (multipart) — requires Bearer token |
| GET | `/users/me` | Get current user profile — requires Bearer token |

Legacy paths `/auth/organiser/send-otp` and `/auth/organiser/verify-otp` remain supported.

## Database Schema

Migration file: `migrations/001_initial_schema.sql`

- `user_role` ENUM: `pending_role_selection`, `crew`, `organiser`, `buyer`
- `kyc_status_type` ENUM: `pending`, `approved`, `rejected`
- `users` table with nullable profile fields until Phase 3
- `otps` table for OTP storage and expiry

## Android Emulator

Point the app at the API with the debug `API_BASE_URL`:

```
http://10.0.2.2:4000/
```

On a physical device, use your machine's LAN IP instead.
