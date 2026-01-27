package com.flipfit.client;

import com.flipfit.bean.GymCustomer;
import com.flipfit.bean.Role;
import com.flipfit.dao.CustomerDAO;
import java.util.Map;

/**
 * Customer DAO CRUD Operations Demo
 * Demonstrates Create, Read, Update, Delete operations
 */
public class CustomerClientApp {
  public static void main(String[] args) {
    CustomerDAO customerDAO = new CustomerDAO();
    
    System.out.println("========================================");
    System.out.println("  CUSTOMER DAO CRUD OPERATIONS DEMO");
    System.out.println("========================================\n");
    
    // ==================== CREATE ====================
    System.out.println("--- CREATE Operation ---");
    GymCustomer customer = new GymCustomer();
    String customerId = customerDAO.generateCustomerId();
    customer.setUserID(customerId);
    customer.setName("John Doe");
    customer.setEmail("john.doe@example.com");
    customer.setPhoneNumber("1234567890");
    customer.setCity("New York");
    customer.setPassword("password123");
    customer.setActive(false); // Customers start inactive
    
    // Set customer role
    Role role = new Role();
    role.setRoleName("CUSTOMER");
    customer.setRole(role);
    
    boolean created = customerDAO.saveCustomer(customer);
    if (created) {
      System.out.println("✓ Customer created successfully!");
      System.out.println("  Customer ID: " + customer.getUserID());
      System.out.println("  Name: " + customer.getName());
      System.out.println("  Email: " + customer.getEmail());
      System.out.println("  City: " + customer.getCity());
      System.out.println("  Active: " + customer.isActive());
    } else {
      System.out.println("✗ Failed to create customer.");
    }
    
    // ==================== READ ====================
    System.out.println("\n--- READ Operations ---");
    
    // Read by Email
    System.out.println("\n1. Read by Email:");
    GymCustomer retrievedByEmail = customerDAO.getCustomerByEmail("john.doe@example.com");
    if (retrievedByEmail != null) {
      System.out.println("✓ Customer found by email:");
      System.out.println("  ID: " + retrievedByEmail.getUserID());
      System.out.println("  Name: " + retrievedByEmail.getName());
      System.out.println("  Phone: " + retrievedByEmail.getPhoneNumber());
    } else {
      System.out.println("✗ Customer not found by email");
    }
    
    // Read by ID
    System.out.println("\n2. Read by ID:");
    GymCustomer retrievedById = customerDAO.getCustomerById(customerId);
    if (retrievedById != null) {
      System.out.println("✓ Customer found by ID:");
      System.out.println("  Name: " + retrievedById.getName());
      System.out.println("  Email: " + retrievedById.getEmail());
      System.out.println("  City: " + retrievedById.getCity());
    } else {
      System.out.println("✗ Customer not found by ID");
    }
    
    // Read All Customers
    System.out.println("\n3. Read All Customers:");
    Map<String, GymCustomer> allCustomers = customerDAO.getAllCustomers();
    System.out.println("✓ Total customers in database: " + allCustomers.size());
    allCustomers.values().forEach(c -> 
      System.out.println("  - " + c.getName() + " (" + c.getEmail() + ") | Active: " + c.isActive())
    );
    
    // Check Email Exists
    System.out.println("\n4. Check Email Exists:");
    boolean emailExists = customerDAO.emailExists("john.doe@example.com");
    System.out.println("✓ Email exists: " + emailExists);
    
    // ==================== UPDATE ====================
    System.out.println("\n--- UPDATE Operation ---");
    if (retrievedByEmail != null) {
      retrievedByEmail.setCity("Los Angeles");
      retrievedByEmail.setPhoneNumber("9876543210");
      retrievedByEmail.setActive(true); // Activate customer
      
      boolean updated = customerDAO.updateCustomer(retrievedByEmail);
      if (updated) {
        System.out.println("✓ Customer updated successfully!");
        
        // Verify update
        GymCustomer updatedCustomer = customerDAO.getCustomerById(customerId);
        System.out.println("  Updated City: " + updatedCustomer.getCity());
        System.out.println("  Updated Phone: " + updatedCustomer.getPhoneNumber());
        System.out.println("  Active Status: " + updatedCustomer.isActive());
      } else {
        System.out.println("✗ Failed to update customer.");
      }
    }
    
    // ==================== DELETE ====================
    System.out.println("\n--- DELETE Operation ---");
    System.out.println("Deleting customer with ID: " + customerId);
    boolean deleted = customerDAO.deleteCustomer(customerId);
    if (deleted) {
      System.out.println("✓ Customer deleted successfully!");
      
      // Verify deletion
      GymCustomer deletedCustomer = customerDAO.getCustomerById(customerId);
      if (deletedCustomer == null) {
        System.out.println("✓ Verified: Customer no longer exists in database");
      }
    } else {
      System.out.println("✗ Failed to delete customer.");
    }
    
    System.out.println("\n========================================");
    System.out.println("  CRUD OPERATIONS COMPLETED");
    System.out.println("========================================");
  }
}
