package com.flipfit.constants;

/**
 * SQL Constants
 * Contains all SQL queries used across the application
 */
public class SQLConstants {
    
    // ==================== ID COUNTER QUERIES ====================
    public static final String UPDATE_COUNTER = "UPDATE id_counters SET current_value = current_value + 1 WHERE counter_name = ?";
    public static final String SELECT_COUNTER = "SELECT current_value FROM id_counters WHERE counter_name = ?";
    
    // ==================== CUSTOMER QUERIES ====================
    public static final String INSERT_CUSTOMER = "INSERT INTO customers (customer_id, name, email, phone_number, city, password, is_active) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String SELECT_CUSTOMER_BY_EMAIL = "SELECT * FROM customers WHERE email = ?";
    public static final String SELECT_CUSTOMER_BY_ID = "SELECT * FROM customers WHERE customer_id = ?";
    public static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM customers";
    public static final String UPDATE_CUSTOMER = "UPDATE customers SET name = ?, phone_number = ?, city = ?, password = ?, is_active = ? WHERE email = ?";
    public static final String DELETE_CUSTOMER = "DELETE FROM customers WHERE customer_id = ?";
    public static final String COUNT_CUSTOMER_BY_EMAIL = "SELECT COUNT(*) FROM customers WHERE email = ?";
    
    // ==================== GYM OWNER QUERIES ====================
    public static final String INSERT_GYM_OWNER = "INSERT INTO gym_owners (owner_id, name, email, phone_number, city, password, pan_number, aadhar_number, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String SELECT_GYM_OWNER_BY_EMAIL = "SELECT * FROM gym_owners WHERE email = ?";
    public static final String SELECT_GYM_OWNER_BY_ID = "SELECT * FROM gym_owners WHERE owner_id = ?";
    public static final String SELECT_ALL_GYM_OWNERS = "SELECT * FROM gym_owners";
    public static final String UPDATE_GYM_OWNER = "UPDATE gym_owners SET name = ?, phone_number = ?, city = ?, password = ?, pan_number = ?, aadhar_number = ?, is_active = ? WHERE email = ?";
    public static final String DELETE_GYM_OWNER = "DELETE FROM gym_owners WHERE owner_id = ?";
    public static final String COUNT_GYM_OWNER_BY_EMAIL = "SELECT COUNT(*) FROM gym_owners WHERE email = ?";
    
    // ==================== ADMIN QUERIES ====================
    public static final String INSERT_ADMIN = "INSERT INTO admins (admin_id, name, email, phone_number, city, password, is_active) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String SELECT_ADMIN_BY_EMAIL = "SELECT * FROM admins WHERE email = ?";
    public static final String SELECT_ADMIN_BY_ID = "SELECT * FROM admins WHERE admin_id = ?";
    public static final String SELECT_ALL_ADMINS = "SELECT * FROM admins";
    public static final String UPDATE_ADMIN = "UPDATE admins SET name = ?, phone_number = ?, city = ?, password = ?, is_active = ? WHERE email = ?";
    public static final String DELETE_ADMIN = "DELETE FROM admins WHERE admin_id = ?";
    public static final String COUNT_ADMIN_BY_EMAIL = "SELECT COUNT(*) FROM admins WHERE email = ?";
    
    // ==================== GYM QUERIES ====================
    public static final String INSERT_GYM = "INSERT INTO gyms (gym_id, gym_name, location, gym_owner_id, is_approved) VALUES (?, ?, ?, ?, ?)";
    public static final String SELECT_GYM_BY_ID = "SELECT * FROM gyms WHERE gym_id = ?";
    public static final String SELECT_ALL_GYMS = "SELECT * FROM gyms";
    public static final String SELECT_GYMS_BY_OWNER_ID = "SELECT * FROM gyms WHERE gym_owner_id = ?";
    public static final String SELECT_GYMS_BY_LOCATION = "SELECT * FROM gyms WHERE location = ? AND is_approved = TRUE";
    public static final String SELECT_APPROVED_GYMS = "SELECT * FROM gyms WHERE is_approved = TRUE";
    public static final String SELECT_PENDING_GYMS = "SELECT * FROM gyms WHERE is_approved = FALSE";
    public static final String UPDATE_GYM = "UPDATE gyms SET gym_name = ?, location = ?, is_approved = ? WHERE gym_id = ?";
    public static final String APPROVE_GYM = "UPDATE gyms SET is_approved = TRUE WHERE gym_id = ?";
    public static final String DELETE_GYM = "DELETE FROM gyms WHERE gym_id = ?";
    
    // ==================== SLOT QUERIES ====================
    public static final String INSERT_SLOT = "INSERT INTO slots (slot_id, gym_id, start_time, end_time, capacity, available_seats) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String SELECT_SLOT_BY_ID = "SELECT * FROM slots WHERE slot_id = ?";
    public static final String SELECT_ALL_SLOTS = "SELECT * FROM slots ORDER BY start_time";
    public static final String SELECT_SLOTS_BY_GYM_ID = "SELECT * FROM slots WHERE gym_id = ? ORDER BY start_time";
    public static final String SELECT_AVAILABLE_SLOTS_BY_GYM_ID = "SELECT * FROM slots WHERE gym_id = ? AND available_seats > 0 ORDER BY start_time";
    public static final String UPDATE_SLOT = "UPDATE slots SET start_time = ?, end_time = ?, capacity = ?, available_seats = ? WHERE slot_id = ?";
    public static final String DECREASE_AVAILABLE_SEATS = "UPDATE slots SET available_seats = available_seats - 1 WHERE slot_id = ? AND available_seats > 0";
    public static final String INCREASE_AVAILABLE_SEATS = "UPDATE slots SET available_seats = available_seats + 1 WHERE slot_id = ? AND available_seats < capacity";
    public static final String DELETE_SLOT = "DELETE FROM slots WHERE slot_id = ?";
    
    // ==================== BOOKING QUERIES ====================
    public static final String INSERT_BOOKING = "INSERT INTO bookings (booking_id, customer_id, slot_id, gym_id, booking_date, status, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String SELECT_BOOKING_BY_ID = "SELECT * FROM bookings WHERE booking_id = ?";
    public static final String SELECT_ALL_BOOKINGS = "SELECT * FROM bookings ORDER BY created_at DESC";
    public static final String SELECT_BOOKINGS_BY_CUSTOMER_ID = "SELECT * FROM bookings WHERE customer_id = ? ORDER BY created_at DESC";
    public static final String SELECT_BOOKINGS_BY_GYM_ID = "SELECT * FROM bookings WHERE gym_id = ? ORDER BY created_at DESC";
    public static final String SELECT_BOOKINGS_BY_SLOT_ID = "SELECT * FROM bookings WHERE slot_id = ? ORDER BY created_at DESC";
    public static final String SELECT_ACTIVE_BOOKINGS_BY_CUSTOMER_ID = "SELECT * FROM bookings WHERE customer_id = ? AND status = 'CONFIRMED' ORDER BY created_at DESC";
    public static final String UPDATE_BOOKING_STATUS = "UPDATE bookings SET status = ? WHERE booking_id = ?";
    public static final String CANCEL_BOOKING = "UPDATE bookings SET status = 'CANCELLED' WHERE booking_id = ?";
    public static final String DELETE_BOOKING = "DELETE FROM bookings WHERE booking_id = ?";
    
    // ==================== PAYMENT QUERIES ====================
    public static final String INSERT_PAYMENT = "INSERT INTO payments (payment_id, booking_id, customer_id, amount, payment_method, payment_status, payment_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String SELECT_PAYMENT_BY_ID = "SELECT * FROM payments WHERE payment_id = ?";
    public static final String SELECT_PAYMENT_BY_BOOKING_ID = "SELECT * FROM payments WHERE booking_id = ?";
    public static final String SELECT_ALL_PAYMENTS = "SELECT * FROM payments ORDER BY payment_date DESC";
    public static final String SELECT_PAYMENTS_BY_CUSTOMER_ID = "SELECT * FROM payments WHERE customer_id = ? ORDER BY payment_date DESC";
    public static final String SELECT_SUCCESSFUL_PAYMENTS_BY_CUSTOMER_ID = "SELECT * FROM payments WHERE customer_id = ? AND payment_status = 'SUCCESS' ORDER BY payment_date DESC";
    public static final String UPDATE_PAYMENT_STATUS = "UPDATE payments SET payment_status = ? WHERE payment_id = ?";
    public static final String DELETE_PAYMENT = "DELETE FROM payments WHERE payment_id = ?";
    public static final String CALCULATE_TOTAL_REVENUE = "SELECT SUM(amount) as total FROM payments WHERE payment_status = 'SUCCESS'";
    public static final String CALCULATE_TOTAL_REVENUE_BY_CUSTOMER = "SELECT SUM(amount) as total FROM payments WHERE customer_id = ? AND payment_status = 'SUCCESS'";
    
    // ==================== NOTIFICATION QUERIES ====================
    public static final String INSERT_NOTIFICATION = "INSERT INTO notifications (notification_id, user_id, user_type, title, message, is_read, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String SELECT_NOTIFICATION_BY_ID = "SELECT * FROM notifications WHERE notification_id = ?";
    public static final String SELECT_ALL_NOTIFICATIONS = "SELECT * FROM notifications ORDER BY created_at DESC";
    public static final String SELECT_NOTIFICATIONS_BY_USER_ID = "SELECT * FROM notifications WHERE user_id = ? ORDER BY created_at DESC";
    public static final String SELECT_UNREAD_NOTIFICATIONS_BY_USER_ID = "SELECT * FROM notifications WHERE user_id = ? AND is_read = FALSE ORDER BY created_at DESC";
    public static final String COUNT_UNREAD_NOTIFICATIONS = "SELECT COUNT(*) as count FROM notifications WHERE user_id = ? AND is_read = FALSE";
    public static final String MARK_NOTIFICATION_AS_READ = "UPDATE notifications SET is_read = TRUE WHERE notification_id = ?";
    public static final String MARK_ALL_NOTIFICATIONS_AS_READ = "UPDATE notifications SET is_read = TRUE WHERE user_id = ?";
    public static final String DELETE_NOTIFICATION = "DELETE FROM notifications WHERE notification_id = ?";
    public static final String DELETE_ALL_NOTIFICATIONS_BY_USER_ID = "DELETE FROM notifications WHERE user_id = ?";
}
