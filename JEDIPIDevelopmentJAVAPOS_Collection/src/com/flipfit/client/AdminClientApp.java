package com.flipfit.client;

import com.flipfit.bean.GymAdmin;
import com.flipfit.bean.Role;
import com.flipfit.dao.AdminDAO;
import java.util.Map;

/**
 * Admin DAO CRUD Operations Demo
 * Demonstrates Create, Read, Update, Delete operations
 */
public class AdminClientApp {
  public static void main(String[] args) {
    AdminDAO adminDAO = new AdminDAO();
    
    System.out.println("========================================");
    System.out.println("  ADMIN DAO CRUD OPERATIONS DEMO");
    System.out.println("========================================\n");
    
    // ==================== CREATE ====================
    System.out.println("--- CREATE Operation ---");
    GymAdmin admin = new GymAdmin();
    String adminId = adminDAO.generateAdminId();
    admin.setUserID(adminId);
    admin.setName("Admin Smith");
    admin.setEmail("admin.smith@flipfit.com");
    admin.setPhoneNumber("5555555555");
    admin.setCity("Delhi");
    admin.setPassword("admin@secure123");
    admin.setActive(true); // Admins are always active
    
    // Set admin role
    Role role = new Role();
    role.setRoleName("ADMIN");
    admin.setRole(role);
    
    boolean created = adminDAO.saveAdmin(admin);
    if (created) {
      System.out.println("✓ Admin created successfully!");
      System.out.println("  Admin ID: " + admin.getUserID());
      System.out.println("  Name: " + admin.getName());
      System.out.println("  Email: " + admin.getEmail());
      System.out.println("  City: " + admin.getCity());
      System.out.println("  Active: " + admin.isActive());
    } else {
      System.out.println("✗ Failed to create admin.");
    }
    
    // ==================== READ ====================
    System.out.println("\n--- READ Operations ---");
    
    // Read by Email
    System.out.println("\n1. Read by Email:");
    GymAdmin retrievedByEmail = adminDAO.getAdminByEmail("admin.smith@flipfit.com");
    if (retrievedByEmail != null) {
      System.out.println("✓ Admin found by email:");
      System.out.println("  ID: " + retrievedByEmail.getUserID());
      System.out.println("  Name: " + retrievedByEmail.getName());
      System.out.println("  Phone: " + retrievedByEmail.getPhoneNumber());
      System.out.println("  Role: " + retrievedByEmail.getRole().getRoleName());
    } else {
      System.out.println("✗ Admin not found by email");
    }
    
    // Read by ID
    System.out.println("\n2. Read by ID:");
    GymAdmin retrievedById = adminDAO.getAdminById(adminId);
    if (retrievedById != null) {
      System.out.println("✓ Admin found by ID:");
      System.out.println("  Name: " + retrievedById.getName());
      System.out.println("  Email: " + retrievedById.getEmail());
      System.out.println("  City: " + retrievedById.getCity());
    } else {
      System.out.println("✗ Admin not found by ID");
    }
    
    // Read All Admins
    System.out.println("\n3. Read All Admins:");
    Map<String, GymAdmin> allAdmins = adminDAO.getAllAdmins();
    System.out.println("✓ Total admins in database: " + allAdmins.size());
    allAdmins.values().forEach(a -> 
      System.out.println("  - " + a.getName() + " (" + a.getEmail() + ") | Active: " + a.isActive())
    );
    
    // Check Email Exists
    System.out.println("\n4. Check Email Exists:");
    boolean emailExists = adminDAO.emailExists("admin.smith@flipfit.com");
    System.out.println("✓ Email exists: " + emailExists);
    
    // ==================== UPDATE ====================
    System.out.println("\n--- UPDATE Operation ---");
    if (retrievedByEmail != null) {
      retrievedByEmail.setCity("Hyderabad");
      retrievedByEmail.setPhoneNumber("6666666666");
      retrievedByEmail.setPassword("newAdminPass123");
      
      boolean updated = adminDAO.updateAdmin(retrievedByEmail);
      if (updated) {
        System.out.println("✓ Admin updated successfully!");
        
        // Verify update
        GymAdmin updatedAdmin = adminDAO.getAdminById(adminId);
        System.out.println("  Updated City: " + updatedAdmin.getCity());
        System.out.println("  Updated Phone: " + updatedAdmin.getPhoneNumber());
        System.out.println("  Active Status: " + updatedAdmin.isActive() + " (Admins always active)");
      } else {
        System.out.println("✗ Failed to update admin.");
      }
    }
    
    // ==================== DELETE ====================
    System.out.println("\n--- DELETE Operation ---");
    System.out.println("Deleting admin with ID: " + adminId);
    boolean deleted = adminDAO.deleteAdmin(adminId);
    if (deleted) {
      System.out.println("✓ Admin deleted successfully!");
      
      // Verify deletion
      GymAdmin deletedAdmin = adminDAO.getAdminById(adminId);
      if (deletedAdmin == null) {
        System.out.println("✓ Verified: Admin no longer exists in database");
      }
    } else {
      System.out.println("✗ Failed to delete admin.");
    }
    
    System.out.println("\n========================================");
    System.out.println("  CRUD OPERATIONS COMPLETED");
    System.out.println("========================================");
  }
}
