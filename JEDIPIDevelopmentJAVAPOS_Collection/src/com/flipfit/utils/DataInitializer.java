package com.flipfit.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.List;
import com.flipfit.bean.*;
import com.flipfit.business.*;
import com.flipfit.exception.*;

public class DataInitializer {

    // Service instances (Adjust based on your actual instantiation logic)
    private static UserService userService = new UserServiceImpl();
    private static GymOwnerService ownerService = new GymOwnerServiceImpl();
    private static AdminService adminService = new AdminServiceImpl();
    private static SlotService slotService = new SlotServiceImpl();

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("    FlipFit Database Initializer");
        System.out.println("========================================");

        // Keep the main branch structure: Initialize database first
        try {
            initializeDatabase();
            System.out.println("\n✓ Database initialized successfully!");

            // Now integrate your branch's exception-handled data creation
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
            
            try {
                userService.register(admin);
                System.out.println("   ✓ Admin created: admin@flipfit.com");
            } catch (RegistrationFailedException | InvalidInputException e) {
                System.err.println("   [WARN] Admin creation skipped: " + e.getMessage());
            }

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
            
            try {
                userService.register(owner1);
                System.out.println("   ✓ Owner 1 created: owner1@flipfit.com");
            } catch (RegistrationFailedException | InvalidInputException e) {
                System.err.println("   [WARN] Owner 1 creation skipped: " + e.getMessage());
            }
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
            
            try {
                userService.register(customer1);
                System.out.println("   ✓ Customer 1 created: customer1@flipfit.com");
            } catch (RegistrationFailedException | InvalidInputException e) {
                System.err.println("   [WARN] Customer 1 creation skipped: " + e.getMessage());
            }

            // 2. Create test gyms
            System.out.println("\n2. Creating test gyms...");
            Gym gym1 = new Gym();
            gym1.setGymName("Gold's Gym Koramangala");
            gym1.setLocation("Bangalore");
            gym1.setGymOwnerId(owner1Id);
            
            try {
                ownerService.registerGym(gym1);
                System.out.println("   ✓ Gym 1 created: Gold's Gym Koramangala");
            } catch (RegistrationFailedException | InvalidInputException e) {
                System.err.println("   [WARN] Gym creation skipped: " + e.getMessage());
            }

            // 3. Approving gyms
            System.out.println("\n3. Approving gyms...");
            List<Gym> ownerGyms = null;
            try {
                ownerGyms = ownerService.viewMyGyms(owner1Id);
                for (Gym gym : ownerGyms) {
                    try {
                        adminService.approveGym(gym.getGymId());
                        System.out.println("   Approved gym: " + gym.getGymId());
                    } catch (ApprovalFailedException | InvalidInputException e) {
                        System.err.println("   [WARN] Could not approve gym " + gym.getGymId() + ": " + e.getMessage());
                    }
                }
            } catch (UserNotFoundException | InvalidInputException e) {
                System.err.println("   [WARN] Could not retrieve or approve gyms: " + e.getMessage());
            }

            // 4. Creating time slots
            System.out.println("\n4. Creating time slots...");
            if (ownerGyms != null && !ownerGyms.isEmpty()) {
                String gym1Id = ownerGyms.get(0).getGymId();
                try {
                    slotService.createSlot(gym1Id, LocalTime.of(6, 0), LocalTime.of(7, 0), 20);
                    System.out.println("   ✓ Slot created for " + gym1Id + " 06:00-07:00");
                } catch (RegistrationFailedException | InvalidInputException e) {
                    System.err.println("   [WARN] Could not create morning slot: " + e.getMessage());
                }

                try {
                    slotService.createSlot(gym1Id, LocalTime.of(7, 0), LocalTime.of(8, 0), 20);
                    System.out.println("   ✓ Slot created for " + gym1Id + " 07:00-08:00");
                } catch (RegistrationFailedException | InvalidInputException e) {
                    System.err.println("   [WARN] Could not create morning slot: " + e.getMessage());
                }
            }

        } catch (Exception e) {
            System.err.println("\n✗ Database initialization failed: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n========================================");
        System.out.println("  TEST DATA INITIALIZATION COMPLETE!");
        System.out.println("========================================\n");
    }

    public static void initializeDatabase() throws Exception {
        // Kept exactly as it was in the 'main' branch
        Connection conn = DatabaseConnection.getInstance().getConnection();
        if (conn == null) {
            throw new Exception("Could not establish database connection.");
        }

        try (Statement stmt = conn.createStatement();
             BufferedReader reader = new BufferedReader(new FileReader("database/schema.sql"))) {
            
            StringBuilder sqlBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("--") || line.trim().isEmpty()) {
                    continue;
                }
                sqlBuilder.append(line).append(" ");

                if (line.trim().endsWith(";")) {
                    String sql = sqlBuilder.toString().trim();
                    if (!sql.isEmpty()) {
                        try {
                            stmt.execute(sql);
                        } catch (Exception e) {
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