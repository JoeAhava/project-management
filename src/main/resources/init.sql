-- V1__init.sql

-- event_publication table
CREATE TABLE IF NOT EXISTS event_publication (
  id UUID PRIMARY KEY,
  completion_date TIMESTAMP WITH TIME ZONE,
  event_type TEXT,
  listener_id TEXT,
  publication_date TIMESTAMP WITH TIME ZONE,
  serialized_event TEXT
);

-- tbl_proposals table
CREATE TABLE IF NOT EXISTS tbl_proposals (
  id UUID PRIMARY KEY,
  title VARCHAR(255),
  short_description TEXT,
  detailed_description TEXT,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
  total_payment_amount NUMERIC(19, 2),
  total_payment_currency VARCHAR(3)
);

-- tbl_milestones table
CREATE TABLE IF NOT EXISTS tbl_milestones (
  id UUID PRIMARY KEY,
  title VARCHAR(255),
  description TEXT,
  detailed_description TEXT,
  payment_amount NUMERIC(19, 2),
  payment_currency VARCHAR(3),
  proposal_id UUID,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
  CONSTRAINT fk_milestone_proposal FOREIGN KEY (proposal_id)
    REFERENCES tbl_proposals (id) ON DELETE CASCADE
);

-- Add columns if missing
ALTER TABLE IF EXISTS tbl_proposals
  ADD COLUMN IF NOT EXISTS total_payment_amount NUMERIC(19, 2);

ALTER TABLE IF EXISTS tbl_proposals
  ADD COLUMN IF NOT EXISTS total_payment_currency VARCHAR(3);

ALTER TABLE IF EXISTS tbl_milestones
  ADD COLUMN IF NOT EXISTS payment_amount NUMERIC(19, 2);

ALTER TABLE IF EXISTS tbl_milestones
  ADD COLUMN IF NOT EXISTS payment_currency VARCHAR(3);

ALTER TABLE IF EXISTS tbl_milestones
  ADD COLUMN IF NOT EXISTS proposal_id UUID;

-- Ensure correct column types (Postgres does not allow IF EXISTS here)
ALTER TABLE tbl_proposals
  ALTER COLUMN total_payment_amount TYPE NUMERIC(19, 2)
  USING total_payment_amount::numeric;

ALTER TABLE tbl_proposals
  ALTER COLUMN total_payment_currency TYPE VARCHAR(3)
  USING total_payment_currency::varchar;

ALTER TABLE tbl_milestones
  ALTER COLUMN payment_amount TYPE NUMERIC(19, 2)
  USING payment_amount::numeric;

ALTER TABLE tbl_milestones
  ALTER COLUMN payment_currency TYPE VARCHAR(3)
  USING payment_currency::varchar;

-- End of migration
