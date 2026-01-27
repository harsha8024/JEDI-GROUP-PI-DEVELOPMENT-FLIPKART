package com.flipfit.client;

import com.flipfit.bean.Gym;
import com.flipfit.bean.Slot;
import com.flipfit.business.GymAdminInterface;
import com.flipfit.exception.ApprovalFailedException;

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
            System.out.println("4. View Pending Slot Approvals");
            System.out.println("5. Approve a Slot");
            System.out.println("6. Reject a Slot");
            System.out.println("7. View All Registered Gyms");
            System.out.println("8. View All Registered Users");
            System.out.println("9. View All Bookings");
            System.out.println("10. Generate Reports");
            System.out.println("11. Logout");
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
                    try {
                        adminService.approveGym(approveId);
                    } catch (ApprovalFailedException e) {
                        System.out.println("[ADMIN ERROR] " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.print("Enter Gym ID to reject: ");
                    String rejectId = scanner.nextLine();
                    try {
                        adminService.rejectGym(rejectId); 
                    } catch (ApprovalFailedException e) {
                        System.out.println("[ADMIN ERROR] " + e.getMessage());
                    }
                    break;
                case 4:
                    viewPendingSlots(adminService);
                    break;
                case 5:
                    System.out.print("Enter Slot ID to approve: ");
                    String approveSlotId = scanner.nextLine();
                    try {
                        adminService.approveSlot(approveSlotId);
                    } catch (ApprovalFailedException e) {
                        System.out.println("[ADMIN ERROR] " + e.getMessage());
                    }
                    break;
                case 6:
                    System.out.print("Enter Slot ID to reject: ");
                    String rejectSlotId = scanner.nextLine();
                    try {
                        adminService.rejectSlot(rejectSlotId);
                        // Successful message is usually handled inside the serviceImpl, 
                        // but we catch the failure here.
                    } catch (ApprovalFailedException e) {
                        System.out.println("\n[ADMIN ACTION FAILED] " + e.getMessage());
                    }
                    break;
                case 7:
                    System.out.println("\n--- All Registered Gyms ---");
                    adminService.viewAllGyms();
                    break;
                case 8:
                    adminService.viewAllUsers();
                    break;
                case 9:
                    System.out.println("\n--- All System Bookings ---");
                    adminService.viewAllBookings();
                    break;
                case 10:
                    handleGenerateReports(scanner, adminService);
                    break;
                case 11:
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
            System.out.println("\n--- Pending Gym Approvals ---");
            pendingGyms.forEach(gym -> 
                System.out.println("ID: " + gym.getGymId() + " | Name: " + gym.getGymName() + " | Location: " + gym.getLocation() + " | Owner: " + gym.getGymOwnerId())
            );
        }
    }
    
    private static void viewPendingSlots(GymAdminInterface adminService) {
        List<Slot> pendingSlots = adminService.viewPendingSlots();
        if (pendingSlots.isEmpty()) {
            System.out.println("\nNo slots currently awaiting approval.");
        } else {
            System.out.println("\n--- Pending Slot Approvals ---");
            pendingSlots.forEach(slot -> 
                System.out.println("Slot ID: " + slot.getSlotId() + 
                                 " | Gym: " + slot.getGymId() + 
                                 " | Time: " + slot.getStartTime() + " - " + slot.getEndTime() + 
                                 " | Capacity: " + slot.getCapacity())
            );
        }
    }

    private static void handleGenerateReports(Scanner scanner, GymAdminInterface adminService) {
        System.out.println("\n--- Report Generation ---");
        System.out.println("1. User Statistics Report");
        System.out.println("2. Gym Statistics Report");
        System.out.println("3. Booking Statistics Report");
        System.out.println("4. Complete System Report");
        System.out.print("Select report type: ");
        int reportType = scanner.nextInt();
        scanner.nextLine();
        
        adminService.generateReports(reportType);
    }
}