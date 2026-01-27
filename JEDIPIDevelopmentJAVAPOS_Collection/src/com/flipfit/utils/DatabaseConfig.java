// TODO: Auto-generated Javadoc
package com.flipfit.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The Class DatabaseConfig.
 *
 * @author team pi
 * @ClassName "DatabaseConfig"
 */
public class DatabaseConfig {

    /** The Constant PROPERTIES_FILE. */
    private static final String PROPERTIES_FILE = "database/database.properties";

    /** The properties. */
    private static Properties properties = new Properties();

    /** The loaded. */
    private static boolean loaded = false;

    /** The Constant DB_URL. */
    // Configuration keys
    private static final String DB_URL = "db.url";

    /** The Constant DB_USERNAME. */
    private static final String DB_USERNAME = "db.username";

    /** The Constant DB_PASSWORD. */
    private static final String DB_PASSWORD = "db.password";

    /** The Constant DB_DRIVER. */
    private static final String DB_DRIVER = "db.driver";

    /**
     * Load properties.
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
     * Sets the default properties.
     */
    private static void setDefaultProperties() {
        properties.setProperty(DB_URL,
                "jdbc:mysql://localhost:3306/flipfit_db?useSSL=false&serverTimezone=Asia/Kolkata");
        properties.setProperty(DB_USERNAME, "root");
        properties.setProperty(DB_PASSWORD, "");
        properties.setProperty(DB_DRIVER, "com.mysql.cj.jdbc.Driver");
        loaded = true;
    }

    /**
     * Gets the url.
     *
     * @return the url
     */
    public static String getUrl() {
        loadProperties();
        return properties.getProperty(DB_URL);
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public static String getUsername() {
        loadProperties();
        return properties.getProperty(DB_USERNAME);
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public static String getPassword() {
        loadProperties();
        return properties.getProperty(DB_PASSWORD);
    }

    /**
     * Gets the driver.
     *
     * @return the driver
     */
    public static String getDriver() {
        loadProperties();
        return properties.getProperty(DB_DRIVER);
    }

    /**
     * Gets the property.
     *
     * @param key the key
     * @return the property
     */
    public static String getProperty(String key) {
        loadProperties();
        return properties.getProperty(key);
    }

    /**
     * Display config.
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
