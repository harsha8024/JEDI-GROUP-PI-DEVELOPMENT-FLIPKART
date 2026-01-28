// TODO: Auto-generated Javadoc
package com.flipfit.client;

import java.util.Map;
import java.util.Scanner;
import com.flipfit.bean.User;
import com.flipfit.business.*;
import com.flipfit.exception.RegistrationFailedException;

/**
 * The Class FlipfitApplication.
 *
 * @author team pi
 * @ClassName "FlipfitApplication"
 */
public class FlipfitApplication {

    /** The Constant ADMIN_EMAIL. */
    // Hardcoded Admin Credentials - Single admin for the system
    private static final String ADMIN_EMAIL = "admin@flipfit.com";

    /** The Constant ADMIN_PASSWORD. */
    private static final String ADMIN_PASSWORD = "admin@123";

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize service implementations
        GymUserInterface userService = new GymUserServiceImpl();
        GymCustomerInterface customerService = new GymCustomerServiceImpl();
        GymOwnerInterface ownerService = new GymOwnerServiceImpl();
        GymAdminInterface adminService = new GymAdminServiceImpl();

        boolean exit = false;

        System.out.println("========================================");
        System.out.println("    Welcome to FlipFit Gym Booking System");
        System.out.println("========================================");

        while (!exit) {
            try {
                displayMainMenu();
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        handleLogin(scanner, userService, customerService, ownerService, adminService);
                        break;
                    case 2:
                        handleRegistration(scanner, userService);
                        break;
                    case 3:
                        handleChangePassword(scanner, userService);
                        break;
                    case 4:
                        // Delegating to the specific Customer Menu class for browsing
                        GymCustomerFlipFitMenu.showCustomerMenu(scanner, customerService);
                        break;
                    case 5:
                        System.out.println("\nThank you for using FlipFit! Stay healthy!");
                        exit = true;
                        break;
                    default:
                        System.out.println("\nInvalid choice! Please try again.");
                }
            } catch (Exception e) {
                System.out.println("\nError: " + e.getMessage());
                scanner.nextLine(); // Clear invalid input
            }
        }
        scanner.close();
    }

    /**
     * Display main menu.
     */
    private static void displayMainMenu() {
        System.out.println("\n========================================");
        System.out.println("            MAIN MENU");
        System.out.println("========================================");
        System.out.println("1. Login\n2. Register\n3. Change Password\n4. Browse as Guest\n5. Exit");
        System.out.println("========================================");
    }

    /**
     * Handle login.
     *
     * @param scanner         the scanner
     * @param userService     the user service
     * @param customerService the customer service
     * @param ownerService    the owner service
     * @param adminService    the admin service
     */
    private static void handleLogin(Scanner scanner, GymUserInterface userService,
            GymCustomerInterface customerService,
            GymOwnerInterface ownerService,
            GymAdminInterface adminService) {

        System.out.println("\n--- Login ---");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        // Check if user is admin with hardcoded credentials
        if (email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD)) {
            System.out.println("\n✓ Admin login successful!");
            System.out.println("Welcome, Administrator!");
            // Admin directly goes to admin menu - no role selection
            GymAdminFlipfitMenu.showAdminMenu(scanner, adminService);
            return;
        }

        // For regular users, validate from database
        try {
            if (userService.login(email, password)) {
                // Get user details to retrieve ID and role
                Map<String, User> userMap = GymUserServiceImpl.getUserMap();
                User loggedInUser = userMap.get(email);

                if (loggedInUser == null) {
                    System.out.println("[ERROR] User not found in system.");
                    return;
                }

                String userId = loggedInUser.getUserID();
                String roleName = loggedInUser.getRole() != null ? loggedInUser.getRole().getRoleName() : "CUSTOMER";

                System.out.println("\n✓ Login successful!");
                System.out.println("Welcome, " + loggedInUser.getName() + "!");

                // Automatically redirect to appropriate menu based on registered role
                switch (roleName.toUpperCase()) {
                    case "CUSTOMER":
                        System.out.println("Logging in as Customer...");
                        GymCustomerFlipFitMenu.showCustomerMenu(scanner, customerService, userId);
                        break;
                    case "OWNER":
                    case "GYM_OWNER":
                        System.out.println("Logging in as Gym Owner...");
                        GymOwnerFlipFitMenu.showOwnerMenu(scanner, ownerService, userId);
                        break;
                    case "ADMIN":
                        System.out.println("Logging in as Admin...");
                        GymAdminFlipfitMenu.showAdminMenu(scanner, adminService);
                        break;
                    default:
                        System.out.println("[ERROR] Unknown role: " + roleName);
                }

            }
        } catch (com.flipfit.exception.UserNotFoundException e) {
            System.out.println("\n[LOGIN ERROR] " + e.getMessage());
        } catch (com.flipfit.exception.InvalidCredentialsException e) {
            System.out.println("\n[AUTH ERROR] " + e.getMessage());
        }
    }

    /**
     * Handle registration.
     *
     * @param scanner     the scanner
     * @param userService the user service
     */
    private static void handleRegistration(Scanner scanner, GymUserInterface userService) {
        System.out.println("\n========================================");
        System.out.println("         REGISTRATION");
        System.out.println("========================================");

        System.out.println("Select your role:");
        System.out.println("1. Customer (Book gym slots)");
        System.out.println("2. Gym Owner (Manage gyms and slots)");
        System.out.print("Enter your choice: ");

        int roleChoice = scanner.nextInt();
        scanner.nextLine();

        String roleName;
        switch (roleChoice) {
            case 1:
                roleName = "CUSTOMER";
                break;
            case 2:
                roleName = "OWNER";
                break;
            default:
                System.out.println("[ERROR] Invalid role selection!");
                return;
        }

        User newUser = new User();
        System.out.println("\n--- Enter Your Details ---");
        System.out.print("Name: ");
        newUser.setName(scanner.nextLine());
        System.out.print("Email: ");
        newUser.setEmail(scanner.nextLine());
        System.out.print("Password: ");
        newUser.setPassword(scanner.nextLine());
        System.out.print("Phone Number: ");
        newUser.setPhoneNumber(scanner.nextLine());
        System.out.print("City: ");
        newUser.setCity(scanner.nextLine());

        com.flipfit.bean.Role role = new com.flipfit.bean.Role();
        role.setRoleName(roleName);
        newUser.setRole(role);

        try {
            if (roleName.equals("OWNER")) {
                com.flipfit.bean.GymOwner owner = new com.flipfit.bean.GymOwner();
                // Mapping common fields
                owner.setName(newUser.getName());
                owner.setEmail(newUser.getEmail());
                owner.setPassword(newUser.getPassword());
                owner.setPhoneNumber(newUser.getPhoneNumber());
                owner.setCity(newUser.getCity());
                owner.setRole(role);

                System.out.print("PAN Number: ");
                owner.setPanNumber(scanner.nextLine());
                System.out.print("Aadhar Number: ");
                owner.setAadharNumber(scanner.nextLine());
                System.out.print("GSTIN Number (optional): ");
                owner.setGstinNumber(scanner.nextLine());

                userService.register(owner);
                System.out.println("\n✓ Gym Owner registration successful! You can now log in.");
            } else {
                userService.register(newUser);
                System.out.println("\n✓ Customer registration successful! You can now log in.");
            }
        } catch (RegistrationFailedException e) {
            System.out.println("\n[REGISTRATION FAILED] " + e.getMessage());
        }
    }

    /**
     * Handle change password.
     *
     * @param scanner     the scanner
     * @param userService the user service
     */
    private static void handleChangePassword(Scanner scanner, GymUserInterface userService) {
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Current Password: ");
        scanner.nextLine(); // Read but don't use (for future validation)
        System.out.print("New Password: ");
        String newPass = scanner.nextLine();

        userService.updatePassword(email, newPass);
    }
}