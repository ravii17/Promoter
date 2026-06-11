-- Promotr Organiser: users, roles, KYC status, OTP storage
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- user_role ENUM
DO $$
BEGIN
  CREATE TYPE user_role AS ENUM (
    'pending_role_selection',
    'crew',
    'organiser',
    'buyer'
  );
EXCEPTION
  WHEN duplicate_object THEN NULL;
END $$;

DO $$
BEGIN
  ALTER TYPE user_role ADD VALUE IF NOT EXISTS 'crew';
EXCEPTION
  WHEN duplicate_object THEN NULL;
END $$;

DO $$
BEGIN
  ALTER TYPE user_role ADD VALUE IF NOT EXISTS 'organiser';
EXCEPTION
  WHEN duplicate_object THEN NULL;
END $$;

DO $$
BEGIN
  ALTER TYPE user_role ADD VALUE IF NOT EXISTS 'buyer';
EXCEPTION
  WHEN duplicate_object THEN NULL;
END $$;

DO $$
BEGIN
  ALTER TYPE user_role ADD VALUE IF NOT EXISTS 'pending_role_selection';
EXCEPTION
  WHEN duplicate_object THEN NULL;
END $$;

-- kyc_status_type ENUM
DO $$
BEGIN
  CREATE TYPE kyc_status_type AS ENUM ('pending', 'approved', 'rejected');
EXCEPTION
  WHEN duplicate_object THEN NULL;
END $$;

DO $$
BEGIN
  ALTER TYPE kyc_status_type ADD VALUE IF NOT EXISTS 'approved';
EXCEPTION
  WHEN duplicate_object THEN NULL;
END $$;

DO $$
BEGIN
  ALTER TYPE kyc_status_type ADD VALUE IF NOT EXISTS 'rejected';
EXCEPTION
  WHEN duplicate_object THEN NULL;
END $$;

-- users table
CREATE TABLE IF NOT EXISTS users (
  user_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  phone VARCHAR UNIQUE,
  email VARCHAR,
  name VARCHAR,
  city VARCHAR,
  role user_role NOT NULL DEFAULT 'pending_role_selection',
  kyc_status kyc_status_type NOT NULL DEFAULT 'pending',
  profile_photo_url VARCHAR,
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
  CONSTRAINT users_phone_or_email CHECK (phone IS NOT NULL OR email IS NOT NULL)
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_users_email ON users (email) WHERE email IS NOT NULL;

ALTER TABLE users ADD COLUMN IF NOT EXISTS city VARCHAR;
ALTER TABLE users ADD COLUMN IF NOT EXISTS profile_photo_url VARCHAR;
ALTER TABLE users ADD COLUMN IF NOT EXISTS created_at TIMESTAMP NOT NULL DEFAULT NOW();
ALTER TABLE users ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP NOT NULL DEFAULT NOW();

-- OTP storage
CREATE TABLE IF NOT EXISTS otps (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  recipient VARCHAR NOT NULL,
  code VARCHAR(6) NOT NULL,
  expires_at TIMESTAMP NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_otps_recipient_expires ON otps (recipient, expires_at);
