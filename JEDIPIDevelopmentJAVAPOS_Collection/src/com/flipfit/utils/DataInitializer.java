package com.flipfit.utils;

import com.flipfit.bean.*;
import com.flipfit.business.*;
import com.flipfit.exception.*;
import java.time.LocalTime;

/**
 * Utility class to initialize test data for the FlipFit system.
 * Updated to handle custom business exceptions.
 */
public class DataInitializer {
    
    public static void initializeTestData() {
        System.out.println("\n========================================");
        System.out.println("  INITIALIZING TEST DATA");
        System.out.println("========================================");
        
        // Initialize services
        GymUserServiceImpl userService = new GymUserServiceImpl();
        GymOwnerServiceImpl ownerService = new GymOwnerServiceImpl();
        GymAdminServiceImpl adminService = new GymAdminServiceImpl();
        SlotServiceImpl slotService = new SlotServiceImpl();
        
        try {
            // 1. Create test users
            System.out.println("\n1. Creating test users...");
            
            // Admin user
            User admin = new User();
            admin.setName("Admin User");
            admin.setEmail("admin@flipfit.com");
            admin.setPassword("admin123");
            admin.setCity("Bangalore");
            admin.setPhoneNumber("9999999999");
            Role adminRole = new Role();
            adminRole.setRoleName("ADMIN");
            admin.setRole(adminRole);
            userService.register(admin);
            System.out.println("   ✓ Admin created: admin@flipfit.com");
            
            // Gym Owner 1
            User owner1 = new User();
            owner1.setName("John Owner");
            owner1.setEmail("owner1@flipfit.com");
            owner1.setPassword("owner123");
            owner1.setCity("Bangalore");
            owner1.setPhoneNumber("9876543210");
            Role ownerRole1 = new Role();
            ownerRole1.setRoleName("OWNER");
            owner1.setRole(ownerRole1);
            userService.register(owner1);
            System.out.println("   ✓ Owner 1 created: owner1@flipfit.com");
            String owner1Id = owner1.getUserID();
            
            // Customer 1
            User customer1 = new User();
            customer1.setName("Mike Customer");
            customer1.setEmail("customer1@flipfit.com");
            customer1.setPassword("customer123");
            customer1.setCity("Bangalore");
            customer1.setPhoneNumber("9123456780");
            Role customerRole1 = new Role();
            customerRole1.setRoleName("CUSTOMER");
            customer1.setRole(customerRole1);
            userService.register(customer1);
            System.out.println("   ✓ Customer 1 created: customer1@flipfit.com");

            // 2. Create test gyms
            System.out.println("\n2. Creating test gyms...");
            
            Gym gym1 = new Gym();
            gym1.setGymName("Gold's Gym Koramangala");
            gym1.setLocation("Bangalore");
            gym1.setGymOwnerId(owner1Id);
            ownerService.registerGym(gym1);
            System.out.println("   ✓ Gym 1 created: Gold's Gym Koramangala");
            
            // 3. Approving gyms
            System.out.println("\n3. Approving gyms...");
            // Use ownerId to fetch specific gyms as per updated interface
            java.util.List<Gym> ownerGyms = ownerService.viewMyGyms(owner1Id);
            for (Gym gym : ownerGyms) {
                adminService.approveGym(gym.getGymId());
            }
            System.out.println("   ✓ All gyms approved");
            
            // 4. Creating time slots
            System.out.println("\n4. Creating time slots...");
            String gym1Id = ownerGyms.get(0).getGymId();
            
            // Morning slots
            slotService.createSlot(gym1Id, LocalTime.of(6, 0), LocalTime.of(7, 0), 20);
            slotService.createSlot(gym1Id, LocalTime.of(7, 0), LocalTime.of(8, 0), 20);
            
            System.out.println("   ✓ Time slots created for Gold's Gym");

            System.out.println("\n========================================");
            System.out.println("  TEST DATA INITIALIZATION COMPLETE!");
            System.out.println("========================================\n");

        } catch (RegistrationFailedException | ApprovalFailedException | UserNotFoundException e) {
            System.err.println("\n[FATAL ERROR] Data Initialization failed: " + e.getMessage());
            System.err.println("Please check if the data already exists in your database.");
        }
    }
    
    public static void main(String[] args) {
        // Initializing with data check
        initializeTestData();
    }
}