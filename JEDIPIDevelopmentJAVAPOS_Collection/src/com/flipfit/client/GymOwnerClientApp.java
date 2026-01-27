package com.flipfit.client;

import com.flipfit.bean.GymOwner;
import com.flipfit.bean.Role;
import com.flipfit.dao.GymOwnerDAO;
import java.util.Map;

/**
 * GymOwner DAO CRUD Operations Demo
 * Demonstrates Create, Read, Update, Delete operations
 */
public class GymOwnerClientApp {
  public static void main(String[] args) {
    GymOwnerDAO gymOwnerDAO = new GymOwnerDAO();
    
    System.out.println("========================================");
    System.out.println("  GYM OWNER DAO CRUD OPERATIONS DEMO");
    System.out.println("========================================\n");
    
    // ==================== CREATE ====================
    System.out.println("--- CREATE Operation ---");
    GymOwner owner = new GymOwner();
    String ownerId = gymOwnerDAO.generateOwnerId();
    owner.setUserID(ownerId);
    owner.setName("Jane Smith");
    owner.setEmail("jane.smith@gymowner.com");
    owner.setPhoneNumber("9876543210");
    owner.setCity("Mumbai");
    owner.setPassword("ownerpass123");
    owner.setPanNumber("ABCDE1234F");
    owner.setAadharNumber("123456789012");
    owner.setActive(false); // Owners start inactive, need admin approval
    
    // Set owner role
    Role role = new Role();
    role.setRoleName("OWNER");
    owner.setRole(role);
    
    boolean created = gymOwnerDAO.saveGymOwner(owner);
    if (created) {
      System.out.println("✓ Gym Owner created successfully!");
      System.out.println("  Owner ID: " + owner.getUserID());
      System.out.println("  Name: " + owner.getName());
      System.out.println("  Email: " + owner.getEmail());
      System.out.println("  City: " + owner.getCity());
      System.out.println("  PAN: " + owner.getPanNumber());
      System.out.println("  Aadhar: " + owner.getAadharNumber());
      System.out.println("  Active: " + owner.isActive());
    } else {
      System.out.println("✗ Failed to create gym owner.");
    }
    
    // ==================== READ ====================
    System.out.println("\n--- READ Operations ---");
    
    // Read by Email
    System.out.println("\n1. Read by Email:");
    GymOwner retrievedByEmail = gymOwnerDAO.getGymOwnerByEmail("jane.smith@gymowner.com");
    if (retrievedByEmail != null) {
      System.out.println("✓ Gym Owner found by email:");
      System.out.println("  ID: " + retrievedByEmail.getUserID());
      System.out.println("  Name: " + retrievedByEmail.getName());
      System.out.println("  Phone: " + retrievedByEmail.getPhoneNumber());
      System.out.println("  PAN: " + retrievedByEmail.getPanNumber());
      System.out.println("  Aadhar: " + retrievedByEmail.getAadharNumber());
    } else {
      System.out.println("✗ Gym Owner not found by email");
    }
    
    // Read by ID
    System.out.println("\n2. Read by ID:");
    GymOwner retrievedById = gymOwnerDAO.getGymOwnerById(ownerId);
    if (retrievedById != null) {
      System.out.println("✓ Gym Owner found by ID:");
      System.out.println("  Name: " + retrievedById.getName());
      System.out.println("  Email: " + retrievedById.getEmail());
      System.out.println("  City: " + retrievedById.getCity());
    } else {
      System.out.println("✗ Gym Owner not found by ID");
    }
    
    // Read All Gym Owners
    System.out.println("\n3. Read All Gym Owners:");
    Map<String, GymOwner> allOwners = gymOwnerDAO.getAllGymOwners();
    System.out.println("✓ Total gym owners in database: " + allOwners.size());
    allOwners.values().forEach(o -> 
      System.out.println("  - " + o.getName() + " (" + o.getEmail() + ") | Active: " + o.isActive())
    );
    
    // Check Email Exists
    System.out.println("\n4. Check Email Exists:");
    boolean emailExists = gymOwnerDAO.emailExists("jane.smith@gymowner.com");
    System.out.println("✓ Email exists: " + emailExists);
    
    // ==================== UPDATE ====================
    System.out.println("\n--- UPDATE Operation ---");
    if (retrievedByEmail != null) {
      retrievedByEmail.setCity("Bangalore");
      retrievedByEmail.setPhoneNumber("9999888877");
      retrievedByEmail.setActive(true); // Admin approved
      retrievedByEmail.setPanNumber("FGHIJ5678K");
      
      boolean updated = gymOwnerDAO.updateGymOwner(retrievedByEmail);
      if (updated) {
        System.out.println("✓ Gym Owner updated successfully!");
        
        // Verify update
        GymOwner updatedOwner = gymOwnerDAO.getGymOwnerById(ownerId);
        System.out.println("  Updated City: " + updatedOwner.getCity());
        System.out.println("  Updated Phone: " + updatedOwner.getPhoneNumber());
        System.out.println("  Updated PAN: " + updatedOwner.getPanNumber());
        System.out.println("  Active Status: " + updatedOwner.isActive());
      } else {
        System.out.println("✗ Failed to update gym owner.");
      }
    }
    
    // ==================== DELETE ====================
    System.out.println("\n--- DELETE Operation ---");
    System.out.println("Deleting gym owner with ID: " + ownerId);
    boolean deleted = gymOwnerDAO.deleteGymOwner(ownerId);
    if (deleted) {
      System.out.println("✓ Gym Owner deleted successfully!");
      
      // Verify deletion
      GymOwner deletedOwner = gymOwnerDAO.getGymOwnerById(ownerId);
      if (deletedOwner == null) {
        System.out.println("✓ Verified: Gym Owner no longer exists in database");
      }
    } else {
      System.out.println("✗ Failed to delete gym owner.");
    }
    
    System.out.println("\n========================================");
    System.out.println("  CRUD OPERATIONS COMPLETED");
    System.out.println("========================================");
  }
}
