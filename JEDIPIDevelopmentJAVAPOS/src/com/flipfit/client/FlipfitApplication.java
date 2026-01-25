package com.flipfit.client;

import java.util.Scanner;
import com.flipfit.bean.User;
import com.flipfit.business.*;

public class FlipfitApplication {

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

    private static void displayMainMenu() {
        System.out.println("\n========================================");
        System.out.println("            MAIN MENU");
        System.out.println("========================================");
        System.out.println("1. Login\n2. Register\n3. Change Password\n4. Browse as Guest\n5. Exit");
        System.out.println("========================================");
    }

    private static void handleLogin(Scanner scanner, GymUserInterface userService,
                                    GymCustomerInterface customerService, 
                                    GymOwnerInterface ownerService,
                                    GymAdminInterface adminService) {
        
        System.out.println("\n--- Login ---");
        System.out.print("Email: ");
        String email = scanner.nextLine(); // This is your loggedInEmail
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (userService.login(email, password)) {
            System.out.println("\nLogin successful!");
            System.out.print("Select Role (1: Customer, 2: Owner, 3: Admin): ");
            int roleChoice = scanner.nextInt();
            scanner.nextLine();

            switch (roleChoice) {
                case 1:
                    // Pass 'email' so the customer service knows who is booking
                    GymCustomerFlipFitMenu.showCustomerMenu(scanner, customerService);
                    break;
                case 2:
                    // Pass 'email' so the owner can update their specific profile
                    GymOwnerFlipFitMenu.showOwnerMenu(scanner, ownerService, email);
                    break;
                case 3:
                    GymAdminFlipfitMenu.showAdminMenu(scanner, adminService);
                    break;
                default:
                    System.out.println("Invalid selection.");
            }
        } else {
            System.out.println("[ERROR] Login failed.");
        }
    }

    private static void handleRegistration(Scanner scanner, GymUserInterface userService) {
        User newUser = new User();
        System.out.println("\n--- Registration ---");
        System.out.print("Name: "); newUser.setName(scanner.nextLine());
        System.out.print("Email: "); newUser.setEmail(scanner.nextLine());
        System.out.print("Password: "); newUser.setPassword(scanner.nextLine());
        System.out.print("City: "); newUser.setCity(scanner.nextLine());
        
        userService.register(newUser);
        System.out.println("Registration successful! You can now log in.");
    }

    private static void handleChangePassword(Scanner scanner, GymUserInterface userService) {
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Current Password: ");
        String oldPass = scanner.nextLine();
        System.out.print("New Password: ");
        String newPass = scanner.nextLine();
        
        userService.updatePassword(email, newPass);
    }
}