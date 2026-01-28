-- ========================================
-- FlipFit Database Migration Script
-- Date: January 28, 2026
-- Purpose: Add GSTIN, Slot Pricing, and Fix Active Status
-- ========================================

USE flipfit_db;

-- ========================================
-- 1. ADD GSTIN NUMBER TO GYM OWNERS
-- ========================================
ALTER TABLE gym_owners 
ADD COLUMN gstin_number VARCHAR(20) AFTER aadhar_number;

-- Add index for faster GSTIN lookups
ALTER TABLE gym_owners 
ADD INDEX idx_gstin (gstin_number);

-- ========================================
-- 2. FIX GYM OWNER ACTIVE STATUS DEFAULT
-- ========================================
-- Change default value for is_active to TRUE
ALTER TABLE gym_owners 
MODIFY COLUMN is_active BOOLEAN DEFAULT TRUE;

-- Update existing gym owners to be active
UPDATE gym_owners 
SET is_active = TRUE 
WHERE is_active = FALSE;

-- ========================================
-- 3. ADD PRICE TO SLOTS
-- ========================================
ALTER TABLE slots 
ADD COLUMN price DECIMAL(10,2) DEFAULT 500.00 AFTER available_seats;

-- Update existing slots to have default price
UPDATE slots 
SET price = 500.00 
WHERE price IS NULL;

-- ========================================
-- 4. VERIFY CHANGES
-- ========================================
-- Check gym_owners table structure
DESCRIBE gym_owners;

-- Check slots table structure
DESCRIBE slots;

-- View sample data
SELECT owner_id, name, email, gstin_number, is_active 
FROM gym_owners 
LIMIT 5;

SELECT slot_id, gym_id, start_time, end_time, price, available_seats 
FROM slots 
LIMIT 5;

-- ========================================
-- 5. VERIFICATION QUERIES
-- ========================================
-- Count active gym owners
SELECT 
    COUNT(*) as total_owners,
    SUM(CASE WHEN is_active = TRUE THEN 1 ELSE 0 END) as active_owners,
    SUM(CASE WHEN is_active = FALSE THEN 1 ELSE 0 END) as inactive_owners
FROM gym_owners;

-- Check slots with pricing
SELECT 
    COUNT(*) as total_slots,
    MIN(price) as min_price,
    MAX(price) as max_price,
    AVG(price) as avg_price
FROM slots;

-- ========================================
-- ROLLBACK SCRIPT (IF NEEDED)
-- ========================================
/*
-- WARNING: Only run this if you need to rollback changes

-- Remove GSTIN column
ALTER TABLE gym_owners DROP COLUMN gstin_number;
ALTER TABLE gym_owners DROP INDEX idx_gstin;

-- Revert is_active default
ALTER TABLE gym_owners MODIFY COLUMN is_active BOOLEAN DEFAULT FALSE;

-- Remove price column
ALTER TABLE slots DROP COLUMN price;
*/

-- ========================================
-- MIGRATION COMPLETE
-- ========================================
SELECT 'Migration completed successfully!' as status;
