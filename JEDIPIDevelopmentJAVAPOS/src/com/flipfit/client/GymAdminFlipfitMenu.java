package com.flipfit.client;

import com.flipfit.bean.Gym;
import com.flipfit.business.GymAdminInterface;
import java.util.List;
import java.util.Scanner;

public class GymAdminFlipfitMenu {

    public static void showAdminMenu(Scanner scanner, GymAdminInterface adminService) {
        boolean back = false;

        while (!back) {
            System.out.println("\n========================================");
            System.out.println("            ADMIN MENU");
            System.out.println("========================================");
            System.out.println("1. View Pending Gym Approvals");
            System.out.println("2. Approve a Gym");
            System.out.println("3. View All Registered Users");
            System.out.println("4. Logout");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewPendingGyms(adminService);
                    break;
                case 2:
                    System.out.print("Enter Gym ID to approve: ");
                    String gymId = scanner.nextLine();
                    adminService.approveGym(gymId);
                    break;
                case 3:
                    adminService.viewAllUsers();
                    break;
                case 4:
                    System.out.println("Logging out from Admin Session...");
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void viewPendingGyms(GymAdminInterface adminService) {
        List<Gym> pendingGyms = adminService.viewPendingApprovals();
        if (pendingGyms.isEmpty()) {
            System.out.println("\nNo gyms currently awaiting approval.");
        } else {
            System.out.println("\n--- Pending Approvals ---");
            pendingGyms.forEach(gym -> 
                System.out.println("ID: " + gym.getGymId() + " | Name: " + gym.getGymName() + " | Location: " + gym.getLocation())
            );
        }
    }
}