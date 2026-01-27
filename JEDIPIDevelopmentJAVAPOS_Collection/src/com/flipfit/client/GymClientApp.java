package com.flipfit.client;

import com.flipfit.bean.Gym;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.Role;
import com.flipfit.dao.GymDAO;
import com.flipfit.dao.GymOwnerDAO;
import java.util.List;

/**
 * Gym DAO CRUD Operations Demo
 * Demonstrates Create, Read, Update, Delete operations
 */
public class GymClientApp {
  public static void main(String[] args) {
    GymDAO gymDAO = new GymDAO();
    GymOwnerDAO gymOwnerDAO = new GymOwnerDAO();
    
    System.out.println("========================================");
    System.out.println("  GYM DAO CRUD OPERATIONS DEMO");
    System.out.println("========================================\n");
    
    // First, create a gym owner for the gym
    System.out.println("--- Setting up Gym Owner ---");
    GymOwner owner = new GymOwner();
    String ownerId = gymOwnerDAO.generateOwnerId();
    owner.setUserID(ownerId);
    owner.setName("Fitness Owner");
    owner.setEmail("fitness.owner@gym.com");
    owner.setPhoneNumber("1112223333");
    owner.setCity("Pune");
    owner.setPassword("ownerpass");
    owner.setPanNumber("XXXXX1111X");
    owner.setAadharNumber("999988887777");
    Role ownerRole = new Role();
    ownerRole.setRoleName("OWNER");
    owner.setRole(ownerRole);
    gymOwnerDAO.saveGymOwner(owner);
    System.out.println("✓ Gym Owner created with ID: " + ownerId);
    
    // ==================== CREATE ====================
    System.out.println("\n--- CREATE Operation ---");
    Gym gym = new Gym();
    String gymId = gymDAO.generateGymId();
    gym.setGymId(gymId);
    gym.setGymName("PowerFit Gym");
    gym.setLocation("Koramangala, Bangalore");
    gym.setGymOwnerId(ownerId);
    gym.setApproved(false); // Gyms start as unapproved
    
    boolean created = gymDAO.saveGym(gym);
    if (created) {
      System.out.println("✓ Gym created successfully!");
      System.out.println("  Gym ID: " + gym.getGymId());
      System.out.println("  Name: " + gym.getGymName());
      System.out.println("  Location: " + gym.getLocation());
      System.out.println("  Owner ID: " + gym.getGymOwnerId());
      System.out.println("  Approved: " + gym.isApproved());
    } else {
      System.out.println("✗ Failed to create gym.");
    }
    
    // ==================== READ ====================
    System.out.println("\n--- READ Operations ---");
    
    // Read by ID
    System.out.println("\n1. Read by ID:");
    Gym retrievedById = gymDAO.getGymById(gymId);
    if (retrievedById != null) {
      System.out.println("✓ Gym found by ID:");
      System.out.println("  Name: " + retrievedById.getGymName());
      System.out.println("  Location: " + retrievedById.getLocation());
      System.out.println("  Owner: " + retrievedById.getGymOwnerId());
      System.out.println("  Approved: " + retrievedById.isApproved());
    } else {
      System.out.println("✗ Gym not found by ID");
    }
    
    // Read All Gyms
    System.out.println("\n2. Read All Gyms:");
    List<Gym> allGyms = gymDAO.getAllGyms();
    System.out.println("✓ Total gyms in database: " + allGyms.size());
    allGyms.forEach(g -> 
      System.out.println("  - " + g.getGymName() + " | " + g.getLocation() + " | Approved: " + g.isApproved())
    );
    
    // Get Gyms by Owner
    System.out.println("\n3. Read Gyms by Owner ID:");
    List<Gym> ownerGyms = gymDAO.getGymsByOwnerId(ownerId);
    System.out.println("✓ Total gyms for owner: " + ownerGyms.size());
    ownerGyms.forEach(g -> 
      System.out.println("  - " + g.getGymName() + " (" + g.getGymId() + ")")
    );
    
    // Get Pending Gyms
    System.out.println("\n4. Read Pending Gyms:");
    List<Gym> pendingGyms = gymDAO.getPendingGyms();
    System.out.println("✓ Pending gyms: " + pendingGyms.size());
    
    // Get Approved Gyms
    System.out.println("\n5. Read Approved Gyms:");
    List<Gym> approvedGyms = gymDAO.getApprovedGyms();
    System.out.println("✓ Approved gyms: " + approvedGyms.size());
    
    // ==================== UPDATE ====================
    System.out.println("\n--- UPDATE Operations ---");
    
    // Update Gym Details
    System.out.println("\n1. Update Gym Details:");
    if (retrievedById != null) {
      retrievedById.setGymName("PowerFit Gym Premium");
      retrievedById.setLocation("Indiranagar, Bangalore");
      
      boolean updated = gymDAO.updateGym(retrievedById);
      if (updated) {
        System.out.println("✓ Gym details updated successfully!");
        
        // Verify update
        Gym updatedGym = gymDAO.getGymById(gymId);
        System.out.println("  Updated Name: " + updatedGym.getGymName());
        System.out.println("  Updated Location: " + updatedGym.getLocation());
      } else {
        System.out.println("✗ Failed to update gym details.");
      }
    }
    
    // Approve Gym
    System.out.println("\n2. Approve Gym:");
    boolean approved = gymDAO.approveGym(gymId);
    if (approved) {
      System.out.println("✓ Gym approved successfully!");
      
      // Verify approval
      Gym approvedGym = gymDAO.getGymById(gymId);
      System.out.println("  Approval Status: " + approvedGym.isApproved());
    } else {
      System.out.println("✗ Failed to approve gym.");
    }
    
    // ==================== DELETE ====================
    System.out.println("\n--- DELETE Operation ---");
    System.out.println("Deleting gym with ID: " + gymId);
    boolean deleted = gymDAO.deleteGym(gymId);
    if (deleted) {
      System.out.println("✓ Gym deleted successfully!");
      
      // Verify deletion
      Gym deletedGym = gymDAO.getGymById(gymId);
      if (deletedGym == null) {
        System.out.println("✓ Verified: Gym no longer exists in database");
      }
    } else {
      System.out.println("✗ Failed to delete gym.");
    }
    
    // Cleanup: Delete owner
    gymOwnerDAO.deleteGymOwner(ownerId);
    
    System.out.println("\n========================================");
    System.out.println("  CRUD OPERATIONS COMPLETED");
    System.out.println("========================================");
  }
}
