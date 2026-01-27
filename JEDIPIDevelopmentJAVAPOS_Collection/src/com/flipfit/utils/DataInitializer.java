package com.flipfit.utils;

import com.flipfit.bean.*;
import com.flipfit.business.*;
import java.time.LocalTime;

/**
 * Utility class to initialize test data for the FlipFit system
 * Run this once to populate the database with sample data
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
        
        // Create test users
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
        System.out.println("   ✓ Admin created: admin@flipfit.com / admin123");
        
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
        System.out.println("   ✓ Owner 1 created: owner1@flipfit.com / owner123");
        String owner1Id = owner1.getUserID();
        
        // Gym Owner 2
        User owner2 = new User();
        owner2.setName("Sarah Owner");
        owner2.setEmail("owner2@flipfit.com");
        owner2.setPassword("owner123");
        owner2.setCity("Mumbai");
        owner2.setPhoneNumber("9876543211");
        Role ownerRole2 = new Role();
        ownerRole2.setRoleName("OWNER");
        owner2.setRole(ownerRole2);
        userService.register(owner2);
        System.out.println("   ✓ Owner 2 created: owner2@flipfit.com / owner123");
        String owner2Id = owner2.getUserID();
        
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
        System.out.println("   ✓ Customer 1 created: customer1@flipfit.com / customer123");
        
        // Customer 2
        User customer2 = new User();
        customer2.setName("Emma Customer");
        customer2.setEmail("customer2@flipfit.com");
        customer2.setPassword("customer123");
        customer2.setCity("Mumbai");
        customer2.setPhoneNumber("9123456781");
        Role customerRole2 = new Role();
        customerRole2.setRoleName("CUSTOMER");
        customer2.setRole(customerRole2);
        userService.register(customer2);
        System.out.println("   ✓ Customer 2 created: customer2@flipfit.com / customer123");
        
        // Create test gyms
        System.out.println("\n2. Creating test gyms...");
        
        // Gym 1 - Bangalore
        Gym gym1 = new Gym();
        gym1.setGymName("Gold's Gym Koramangala");
        gym1.setLocation("Bangalore");
        gym1.setGymOwnerId(owner1Id);
        ownerService.registerGym(gym1);
        System.out.println("   ✓ Gym 1 created: Gold's Gym Koramangala");
        
        // Gym 2 - Bangalore
        Gym gym2 = new Gym();
        gym2.setGymName("Fitness First Indiranagar");
        gym2.setLocation("Bangalore");
        gym2.setGymOwnerId(owner1Id);
        ownerService.registerGym(gym2);
        System.out.println("   ✓ Gym 2 created: Fitness First Indiranagar");
        
        // Gym 3 - Mumbai
        Gym gym3 = new Gym();
        gym3.setGymName("Talwalkars Andheri");
        gym3.setLocation("Mumbai");
        gym3.setGymOwnerId(owner2Id);
        ownerService.registerGym(gym3);
        System.out.println("   ✓ Gym 3 created: Talwalkars Andheri");
        
        // Approve gyms
        System.out.println("\n3. Approving gyms...");
        java.util.List<Gym> allGyms = ownerService.viewMyGyms();
        for (Gym gym : allGyms) {
            adminService.approveGym(gym.getGymId());
        }
        System.out.println("   ✓ All gyms approved");
        
        // Create slots for gyms
        System.out.println("\n4. Creating time slots...");
        
        // Get gym IDs
        String gym1Id = allGyms.get(0).getGymId();
        String gym2Id = allGyms.get(1).getGymId();
        String gym3Id = allGyms.get(2).getGymId();
        
        // Morning slots for Gym 1
        slotService.createSlot(gym1Id, LocalTime.of(6, 0), LocalTime.of(7, 0), 20);
        slotService.createSlot(gym1Id, LocalTime.of(7, 0), LocalTime.of(8, 0), 20);
        slotService.createSlot(gym1Id, LocalTime.of(8, 0), LocalTime.of(9, 0), 15);
        
        // Evening slots for Gym 1
        slotService.createSlot(gym1Id, LocalTime.of(18, 0), LocalTime.of(19, 0), 25);
        slotService.createSlot(gym1Id, LocalTime.of(19, 0), LocalTime.of(20, 0), 25);
        slotService.createSlot(gym1Id, LocalTime.of(20, 0), LocalTime.of(21, 0), 20);
        
        System.out.println("   ✓ 6 slots created for Gold's Gym");
        
        // Morning slots for Gym 2
        slotService.createSlot(gym2Id, LocalTime.of(6, 0), LocalTime.of(7, 0), 15);
        slotService.createSlot(gym2Id, LocalTime.of(7, 0), LocalTime.of(8, 0), 15);
        slotService.createSlot(gym2Id, LocalTime.of(8, 0), LocalTime.of(9, 0), 15);
        
        // Evening slots for Gym 2
        slotService.createSlot(gym2Id, LocalTime.of(18, 0), LocalTime.of(19, 0), 20);
        slotService.createSlot(gym2Id, LocalTime.of(19, 0), LocalTime.of(20, 0), 20);
        
        System.out.println("   ✓ 5 slots created for Fitness First");
        
        // Slots for Gym 3 (Mumbai)
        slotService.createSlot(gym3Id, LocalTime.of(6, 0), LocalTime.of(7, 0), 30);
        slotService.createSlot(gym3Id, LocalTime.of(7, 0), LocalTime.of(8, 0), 30);
        slotService.createSlot(gym3Id, LocalTime.of(18, 0), LocalTime.of(19, 0), 30);
        slotService.createSlot(gym3Id, LocalTime.of(19, 0), LocalTime.of(20, 0), 30);
        
        System.out.println("   ✓ 4 slots created for Talwalkars");
        
        System.out.println("\n========================================");
        System.out.println("  TEST DATA INITIALIZATION COMPLETE!");
        System.out.println("========================================");
        
        System.out.println("\nYou can now login with:");
        System.out.println("\n--- Admin ---");
        System.out.println("Email: admin@flipfit.com");
        System.out.println("Password: admin123");
        
        System.out.println("\n--- Gym Owners ---");
        System.out.println("Email: owner1@flipfit.com (Bangalore gyms)");
        System.out.println("Email: owner2@flipfit.com (Mumbai gyms)");
        System.out.println("Password: owner123");
        
        System.out.println("\n--- Customers ---");
        System.out.println("Email: customer1@flipfit.com (Bangalore)");
        System.out.println("Email: customer2@flipfit.com (Mumbai)");
        System.out.println("Password: customer123");
        System.out.println("\n========================================\n");
    }
    
    public static void main(String[] args) {
        // Check if database already has data
        java.util.Map<String, User> existingUsers = LocalFileDatabase.loadUsers();
        
        if (!existingUsers.isEmpty()) {
            System.out.println("\n⚠ Warning: Database already contains data!");
            System.out.println("Found " + existingUsers.size() + " existing users.");
            System.out.println("\nDo you want to reinitialize? This will ADD to existing data.");
            System.out.print("Continue? (yes/no): ");
            
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            String response = scanner.nextLine();
            
            if (!response.equalsIgnoreCase("yes") && !response.equalsIgnoreCase("y")) {
                System.out.println("Initialization cancelled.");
                scanner.close();
                return;
            }
            scanner.close();
        }
        
        initializeTestData();
    }
}
