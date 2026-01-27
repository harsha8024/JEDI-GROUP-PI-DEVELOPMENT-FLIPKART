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
            System.out.println("3. Reject a Gym");
            System.out.println("4. View All Registered Gyms");
            System.out.println("5. View All Registered Users");
            System.out.println("6. View All Bookings");
            System.out.println("7. Generate Reports");
            System.out.println("8. Logout");
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
                    String approveId = scanner.nextLine();
                    adminService.approveGym(approveId);
                    break;
                case 3:
                    System.out.print("Enter Gym ID to reject: ");
                    String rejectId = scanner.nextLine();
                    adminService.rejectGym(rejectId); 
                    System.out.println("Gym " + rejectId + " has been rejected.");
                    break;
                case 4:
                    System.out.println("\n--- All Registered Gyms ---");
                     adminService.viewAllGyms();
                    break;
                case 5:
                    adminService.viewAllUsers();
                    break;
                case 6:
                    System.out.println("\n--- All System Bookings ---");
                     adminService.viewAllBookings();
                    break;
                case 7:
                    handleGenerateReports(scanner, adminService);
                    break;
                case 8:
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

    private static void handleGenerateReports(Scanner scanner, GymAdminInterface adminService) {
        System.out.println("\n--- Report Generation ---");
        System.out.println("1. Daily Revenue Report");
        System.out.println("2. User Registration Report");
        System.out.println("3. Gym Utilization Report");
        System.out.print("Select report type: ");
        int reportType = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("Generating report type " + reportType + "...");
        // adminService.generateReports(reportType);
    }
}