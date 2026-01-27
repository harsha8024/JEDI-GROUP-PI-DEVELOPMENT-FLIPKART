/**
 * 
 */
package com.flipkart.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import java.sql.*;
/**
 * 
 */
public class CustomerDAO {
	// Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Flipfit_schema";
    private static final String USER = "root"; // Replace with your MySQL username
    private static final String PASS = "Viayai3A!@#%"; // Replace with your MySQL password

    // Method to get a database connection
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    // --- C: Create (Insert) ---
    public void insertCustomer(int ID, String Name, String Email, String Contact) {
        String sql = "INSERT INTO Customer (ID, Name, Email, Contact) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) { //
            pstmt.setInt(1, ID);

            pstmt.setString(2, Name);
            pstmt.setString(3, Email);
            pstmt.setString(4, Contact);

            int rowsAffected = pstmt.executeUpdate(); //
            System.out.println(rowsAffected + " customer inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- R: Read (Select) ---
    public void selectAllCustomers() {
        String sql = "SELECT * FROM Customer";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) { //

            System.out.println("--- Customer Records ---");
            while (rs.next()) {
                int ID = rs.getInt("ID");
                String Name = rs.getString("Name");
                String Email = rs.getString("Email");
                String Contact = rs.getString("Contact");
                System.out.printf("ID: %d, Name: %s, Email: %s, Contact: %s%n", ID, Name, Email, Contact);
            }
            System.out.println("------------------------");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- U: Update ---
    public void updateCustomerContact(int ID, String newContact) {
        String sql = "UPDATE Customer SET Contact = ? WHERE ID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) { //
            pstmt.setString(1, newContact);
            pstmt.setInt(2, ID);

            int rowsAffected = pstmt.executeUpdate(); //
            System.out.println(rowsAffected + " customer contact updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- D: Delete ---
    public void deleteCustomer(int ID) {
        String sql = "DELETE FROM Customer WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ID);

            int rowsAffected = pstmt.executeUpdate(); //
            System.out.println(rowsAffected + " customer deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
