package com.flipfit.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Database Configuration Class
 * Loads and manages database connection properties from database.properties file
 */
public class DatabaseConfig {
    
    private static final String PROPERTIES_FILE = "database/database.properties";
    private static Properties properties = new Properties();
    private static boolean loaded = false;
    
    // Configuration keys
    private static final String DB_URL = "db.url";
    private static final String DB_USERNAME = "db.username";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_DRIVER = "db.driver";
    
    /**
     * Load database properties from file
     */
    private static void loadProperties() {
        if (loaded) {
            return;
        }
        
        try (InputStream input = new FileInputStream(PROPERTIES_FILE)) {
            properties.load(input);
            loaded = true;
            System.out.println("✓ Database configuration loaded successfully");
        } catch (IOException e) {
            System.err.println("Error loading database configuration: " + e.getMessage());
            System.err.println("Using default configuration...");
            setDefaultProperties();
        }
    }
    
    /**
     * Set default properties if file is not found
     */
    private static void setDefaultProperties() {
        properties.setProperty(DB_URL, "jdbc:mysql://localhost:3306/flipfit_db?useSSL=false&serverTimezone=Asia/Kolkata");
        properties.setProperty(DB_USERNAME, "root");
        properties.setProperty(DB_PASSWORD, "");
        properties.setProperty(DB_DRIVER, "com.mysql.cj.jdbc.Driver");
        loaded = true;
    }
    
    /**
     * Get database URL
     */
    public static String getUrl() {
        loadProperties();
        return properties.getProperty(DB_URL);
    }
    
    /**
     * Get database username
     */
    public static String getUsername() {
        loadProperties();
        return properties.getProperty(DB_USERNAME);
    }
    
    /**
     * Get database password
     */
    public static String getPassword() {
        loadProperties();
        return properties.getProperty(DB_PASSWORD);
    }
    
    /**
     * Get JDBC driver class name
     */
    public static String getDriver() {
        loadProperties();
        return properties.getProperty(DB_DRIVER);
    }
    
    /**
     * Get specific property by key
     */
    public static String getProperty(String key) {
        loadProperties();
        return properties.getProperty(key);
    }
    
    /**
     * Display current configuration (for debugging)
     */
    public static void displayConfig() {
        loadProperties();
        System.out.println("\n========================================");
        System.out.println("  DATABASE CONFIGURATION");
        System.out.println("========================================");
        System.out.println("URL: " + getUrl());
        System.out.println("Username: " + getUsername());
        System.out.println("Password: " + (getPassword().isEmpty() ? "(empty)" : "******"));
        System.out.println("Driver: " + getDriver());
        System.out.println("========================================\n");
    }
}
