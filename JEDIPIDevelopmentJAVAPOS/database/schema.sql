-- ========================================
-- FlipFit POS System - MySQL Database Schema
-- ========================================
-- Drop database if exists (optional - use with caution)
-- DROP DATABASE IF EXISTS flipfit_db;

-- Create database
CREATE DATABASE IF NOT EXISTS flipfit_db;
USE flipfit_db;

-- ========================================
-- TABLE: roles
-- Stores role information for normalization
-- ========================================
CREATE TABLE IF NOT EXISTS roles (
    role_name VARCHAR(20) PRIMARY KEY,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Initialize roles
INSERT INTO roles (role_name, description) VALUES
    ('ADMIN', 'System Administrator'),
    ('OWNER', 'Gym Owner'),
    ('CUSTOMER', 'Gym Customer')
ON DUPLICATE KEY UPDATE role_name=role_name;

-- ========================================
-- TABLE: users
-- Stores all user information (customers, owners, admins)
-- ========================================
CREATE TABLE IF NOT EXISTS users (
    user_id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(15),
    city VARCHAR(50),
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (role) REFERENCES roles(role_name),
    INDEX idx_email (email),
    INDEX idx_role (role),
    INDEX idx_city (city)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ========================================
-- TABLE: gyms
-- Stores gym information
-- ========================================
CREATE TABLE IF NOT EXISTS gyms (
    gym_id VARCHAR(50) PRIMARY KEY,
    gym_name VARCHAR(100) NOT NULL,
    location VARCHAR(100) NOT NULL,
    gym_owner_id VARCHAR(50) NOT NULL,
    is_approved BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (gym_owner_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_owner (gym_owner_id),
    INDEX idx_location (location),
    INDEX idx_approved (is_approved)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ========================================
-- TABLE: slots
-- Stores time slots for gyms
-- ========================================
CREATE TABLE IF NOT EXISTS slots (
    slot_id VARCHAR(50) PRIMARY KEY,
    gym_id VARCHAR(50) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    capacity INT NOT NULL,
    available_seats INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (gym_id) REFERENCES gyms(gym_id) ON DELETE CASCADE,
    INDEX idx_gym (gym_id),
    INDEX idx_time (start_time, end_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ========================================
-- TABLE: bookings
-- Stores booking information
-- ========================================
CREATE TABLE IF NOT EXISTS bookings (
    booking_id VARCHAR(50) PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL,
    slot_id VARCHAR(50) NOT NULL,
    gym_id VARCHAR(50) NOT NULL,
    booking_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (slot_id) REFERENCES slots(slot_id) ON DELETE CASCADE,
    FOREIGN KEY (gym_id) REFERENCES gyms(gym_id) ON DELETE CASCADE,
    INDEX idx_user (user_id),
    INDEX idx_slot (slot_id),
    INDEX idx_gym (gym_id),
    INDEX idx_date (booking_date),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ========================================
-- TABLE: payments
-- Stores payment information (For Assignment 3)
-- ========================================
CREATE TABLE IF NOT EXISTS payments (
    payment_id VARCHAR(50) PRIMARY KEY,
    booking_id VARCHAR(50) NOT NULL,
    user_id VARCHAR(50) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(50),
    payment_status VARCHAR(20) DEFAULT 'SUCCESS',
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_booking (booking_id),
    INDEX idx_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ========================================
-- TABLE: notifications
-- Stores user notifications (For Assignment 3)
-- ========================================
CREATE TABLE IF NOT EXISTS notifications (
    notification_id VARCHAR(50) PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL,
    title VARCHAR(200) NOT NULL,
    message TEXT NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ========================================
-- VIEW: gym_customers
-- View for gym customers (For Assignment 3)
-- ========================================
CREATE OR REPLACE VIEW gym_customers AS
SELECT * FROM users WHERE role = 'CUSTOMER';

-- ========================================
-- VIEW: gym_owners
-- View for gym owners (For Assignment 3)
-- ========================================
CREATE OR REPLACE VIEW gym_owners AS
SELECT * FROM users WHERE role = 'OWNER';

-- ========================================
-- TABLE: id_counters
-- Stores auto-increment counters for ID generation
-- ========================================
CREATE TABLE IF NOT EXISTS id_counters (
    counter_name VARCHAR(20) PRIMARY KEY,
    current_value INT NOT NULL DEFAULT 1000
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Initialize counters
INSERT INTO id_counters (counter_name, current_value) VALUES
    ('USER', 1000),
    ('GYM', 2000),
    ('SLOT', 3000),
    ('BOOKING', 4000),
    ('PAYMENT', 5000),
    ('NOTIFICATION', 6000)
ON DUPLICATE KEY UPDATE counter_name=counter_name;
