package com.flipfit.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Connection Manager
 * Singleton pattern implementation for managing database connections
 * Thread-safe connection handling
 */
public class DatabaseConnection {
    
    private static DatabaseConnection instance;
    private static final int MAX_RETRIES = 3;
    
    /**
     * Private constructor to prevent instantiation
     */
    private DatabaseConnection() {
        try {
            // Load MySQL JDBC Driver
            Class.forName(DatabaseConfig.getDriver());
            System.out.println("✓ MySQL JDBC Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            System.err.println("✗ MySQL JDBC Driver not found!");
            System.err.println("Please add MySQL Connector/J to your project's build path");
            throw new RuntimeException("Failed to load MySQL JDBC Driver", e);
        }
    }
    
    /**
     * Get singleton instance
     */
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    /**
     * Get a database connection
     * Implements retry logic for transient failures
     */
    public Connection getConnection() throws SQLException {
        int retries = 0;
        SQLException lastException = null;
        
        while (retries < MAX_RETRIES) {
            try {
                Connection connection = DriverManager.getConnection(
                    DatabaseConfig.getUrl(),
                    DatabaseConfig.getUsername(),
                    DatabaseConfig.getPassword()
                );
                
                if (retries > 0) {
                    System.out.println("✓ Database connection established (after " + retries + " retries)");
                } else {
                    System.out.println("✓ Database connection established");
                }
                
                return connection;
                
            } catch (SQLException e) {
                lastException = e;
                retries++;
                
                if (retries < MAX_RETRIES) {
                    System.err.println("Connection attempt " + retries + " failed. Retrying...");
                    try {
                        Thread.sleep(1000); // Wait 1 second before retry
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new SQLException("Connection retry interrupted", ie);
                    }
                }
            }
        }
        
        System.err.println("✗ Failed to connect to database after " + MAX_RETRIES + " attempts");
        System.err.println("Error: " + lastException.getMessage());
        throw lastException;
    }
    
    /**
     * Close a database connection safely
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("✓ Database connection closed");
                }
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
    
    /**
     * Test database connectivity
     */
    public boolean testConnection() {
        try (Connection connection = getConnection()) {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Display connection information
     */
    public void displayConnectionInfo() {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                System.out.println("\n========================================");
                System.out.println("  DATABASE CONNECTION INFO");
                System.out.println("========================================");
                System.out.println("Database: " + connection.getCatalog());
                System.out.println("URL: " + connection.getMetaData().getURL());
                System.out.println("User: " + connection.getMetaData().getUserName());
                System.out.println("Driver: " + connection.getMetaData().getDriverName());
                System.out.println("Driver Version: " + connection.getMetaData().getDriverVersion());
                System.out.println("========================================\n");
            }
        } catch (SQLException e) {
            System.err.println("Error getting connection info: " + e.getMessage());
        }
    }
    
    /**
     * Main method for testing
     */
    public static void main(String[] args) {
        System.out.println("\n========================================");
        System.out.println("  TESTING DATABASE CONNECTION");
        System.out.println("========================================\n");
        
        DatabaseConfig.displayConfig();
        
        DatabaseConnection dbManager = DatabaseConnection.getInstance();
        
        if (dbManager.testConnection()) {
            System.out.println("\n✓ Database connection test PASSED");
            dbManager.displayConnectionInfo();
        } else {
            System.out.println("\n✗ Database connection test FAILED");
            System.out.println("\nPlease check:");
            System.out.println("1. MySQL server is running");
            System.out.println("2. Database 'flipfit_db' exists");
            System.out.println("3. Username and password are correct");
            System.out.println("4. MySQL Connector/J JAR is in classpath");
        }
    }
}
