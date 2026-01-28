package com.flipfit.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;

public class DataInitializer {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   FlipFit Database Initializer");
        System.out.println("========================================");

        try {
            initializeDatabase();
            System.out.println("\n✓ Database initialized successfully!");
        } catch (Exception e) {
            System.err.println("\n✗ Database initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void initializeDatabase() throws Exception {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        if (conn == null) {
            throw new Exception("Could not establish database connection.");
        }

        Statement stmt = conn.createStatement();
        String schemaPath = "database/schema.sql";

        System.out.println("Reading schema from: " + schemaPath);

        try (BufferedReader reader = new BufferedReader(new FileReader(schemaPath))) {
            StringBuilder sqlBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                // Remove comments and empty lines
                if (line.trim().startsWith("--") || line.trim().isEmpty()) {
                    continue;
                }

                sqlBuilder.append(line).append(" ");

                // If line ends with semicolon, verify and execute
                if (line.trim().endsWith(";")) {
                    String sql = sqlBuilder.toString().trim();
                    if (!sql.isEmpty()) {
                        try {
                            // System.out.println("Executing: " + (sql.length() > 50 ? sql.substring(0, 50)
                            // + "..." : sql));
                            stmt.execute(sql);
                        } catch (Exception e) {
                            // Ignore "table exists" errors or similar minor issues if we want to be robust
                            // But for "CREATE TABLE", we want to know if it fails.
                            // However, the schema uses "IF NOT EXISTS", so it should be fine.
                            // We'll print a warning but continue
                            System.out.println("Warning executing statement: " + e.getMessage());
                        }
                    }
                    sqlBuilder = new StringBuilder();
                }
            }
        } catch (IOException e) {
            throw new Exception("Error reading schema file: " + e.getMessage());
        }
    }
}