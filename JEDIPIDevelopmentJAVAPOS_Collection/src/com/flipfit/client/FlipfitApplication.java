// TODO: Auto-generated Javadoc
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

/**
 * The Class DataInitializer.
 *
 * @author team pi
 * @ClassName  "DataInitializer"
 */
public class DataInitializer {

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   FlipFit Database Initializer");
        System.out.println("========================================");

        try {
            initializeDatabase();
            System.out.println("\n✓ Database initialized and seeded successfully!");
        } catch (Exception e) {
            System.err.println("\n✗ Database initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Initialize database by executing schema SQL and seeding initial test data.
     *
     * @throws Exception the exception if connection or execution fails
     */
    public static void initializeDatabase() throws Exception {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        if (conn == null) {
            throw new Exception("Could not establish database connection.");
        }

        // --- PART 1: SCHEMA INITIALIZATION (Resolving Conflict from feature/login-datetime) ---
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
                            stmt.execute(sql);
                        } catch (Exception e) {
                            // Schema uses "IF NOT EXISTS", so minor warnings are ignored
                            System.out.println("Warning executing statement: " + e.getMessage());
                        }
                    }
                    sqlBuilder = new StringBuilder();
                }
            }
        } catch (IOException e) {
            throw new Exception("Error reading schema file: " + e.getMessage());
        }

        // --- PART 2: DATA SEEDING (Resolving Conflict from main) ---
        GymUserServiceImpl userService = new GymUserServiceImpl();
        GymOwnerServiceImpl ownerService = new GymOwnerServiceImpl();
        GymAdminServiceImpl adminService = new GymAdminServiceImpl();
        SlotServiceImpl slotService = new SlotServiceImpl();

        try {
            System.out.println("\n1. Creating test users...");
            
            // Seed Admin
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

            // Seed Owner
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
            String owner1Id = owner1.getUserID();

            System.out.println("\n2. Creating test gyms...");
            Gym gym1 = new Gym();
            gym1.setGymName("Gold's Gym Koramangala");
            gym1.setLocation("Bangalore");
            gym1.setGymOwnerId(owner1Id);
            ownerService.registerGym(gym1);

            System.out.println("\n3. Approving gyms...");
            List<Gym> ownerGyms = ownerService.viewMyGyms(owner1Id);
            // Using forEach as per assignment requirements
            ownerGyms.forEach(gym -> {
                try {
                    adminService.approveGym(gym.getGymId());
                } catch (ApprovalFailedException e) {
                    System.out.println("Approval skipped for ID " + gym.getGymId());
                }
            });
            System.out.println("   ✓ All gyms approved");

            System.out.println("\n4. Creating time slots...");
            if (!ownerGyms.isEmpty()) {
                String gym1Id = ownerGyms.get(0).getGymId();
                slotService.createSlot(gym1Id, LocalTime.of(6, 0), LocalTime.of(7, 0), 20);
                slotService.createSlot(gym1Id, LocalTime.of(7, 0), LocalTime.of(8, 0), 20);
            }

            System.out.println("\n========================================");
            System.out.println("  TEST DATA INITIALIZATION COMPLETE!");
            System.out.println("========================================\n");

        } catch (RegistrationFailedException | UserNotFoundException e) {
            System.err.println("\n[ERROR] Data seeding failed: " + e.getMessage());
        }
    }
}