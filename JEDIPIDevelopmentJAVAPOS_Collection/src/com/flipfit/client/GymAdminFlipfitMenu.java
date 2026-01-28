// TODO: Auto-generated Javadoc
package com.flipfit.client;

import com.flipfit.bean.Gym;
import com.flipfit.bean.Slot;
import com.flipfit.business.GymAdminInterface;
import com.flipfit.exception.ApprovalFailedException;

import java.util.List;
import java.util.Scanner;

/**
 * The Class GymAdminFlipfitMenu.
 *
 * @author team pi
 * @ClassName "GymAdminFlipfitMenu"
 */
public class GymAdminFlipfitMenu {

    /**
     * Show admin menu.
     *
     * @param scanner      the scanner
     * @param adminService the admin service
     */
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
            System.out.println("11. View Payment & Revenue Reports");
            System.out.println("12. View Revenue by Date Range");
            System.out.println("13. View Approved Gyms");
            System.out.println("14. View Approved Gym Owners");
            System.out.println("15. View Pending Gym Owners");
            System.out.println("16. Approve a Gym Owner");
            System.out.println("17. Reject a Gym Owner");
            System.out.println("18. Filter Gym Centers");
            System.out.println("19. Logout");
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
                    adminService.viewPaymentReports();
                    break;
                case 12:
                    System.out.print("Enter start date (yyyy-MM-dd): ");
                    String startDate = scanner.nextLine();
                    System.out.print("Enter end date (yyyy-MM-dd): ");
                    String endDate = scanner.nextLine();
                    adminService.viewRevenueByDateRange(startDate, endDate);
                    break;
                case 13:
                    adminService.viewApprovedGyms();
                    break;
                case 14:
                    adminService.viewApprovedGymOwners();
                    break;
                case 15:
                    adminService.viewPendingGymOwners();
                    break;
                case 16:
                    System.out.print("Enter FULL Gym Owner ID to approve (e.g., OWN1500): ");
                    String ownerId = scanner.nextLine();
                    try {
                        adminService.approveGymOwner(ownerId);
                    } catch (ApprovalFailedException e) {
                        System.out.println("[ADMIN ERROR] " + e.getMessage());
                    }
                    break;
                case 17:
                    System.out.print("Enter FULL Gym Owner ID to reject (e.g., OWN1500): ");
                    String rejectOwnerId = scanner.nextLine();
                    try {
                        adminService.rejectGymOwner(rejectOwnerId);
                    } catch (ApprovalFailedException e) {
                        System.out.println("[ADMIN ERROR] " + e.getMessage());
                    }
                    break;
                case 18:
                    filterGymCenters(scanner, adminService);
                    break;
                case 19:
                    System.out.println("Logging out from Admin Session...");
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    /**
     * View pending gyms.
     *
     * @param adminService the admin service
     */
    private static void viewPendingGyms(GymAdminInterface adminService) {
        List<Gym> pendingGyms = adminService.viewPendingApprovals();
        if (pendingGyms.isEmpty()) {
            System.out.println("\nNo gyms currently awaiting approval.");
        } else {
            System.out.println("\n--- Pending Gym Approvals ---");
            pendingGyms.forEach(gym -> System.out.println("ID: " + gym.getGymId() + " | Name: " + gym.getGymName()
                    + " | Location: " + gym.getLocation() + " | Owner: " + gym.getGymOwnerId()));
        }
    }

    /**
     * View pending slots.
     *
     * @param adminService the admin service
     */
    private static void viewPendingSlots(GymAdminInterface adminService) {
        List<Slot> pendingSlots = adminService.viewPendingSlots();
        if (pendingSlots.isEmpty()) {
            System.out.println("\nNo slots currently awaiting approval.");
        } else {
            System.out.println("\n--- Pending Slot Approvals ---");
            pendingSlots.forEach(slot -> System.out.println("Slot ID: " + slot.getSlotId() +
                    " | Gym: " + slot.getGymId() +
                    " | Time: " + slot.getStartTime() + " - " + slot.getEndTime() +
                    " | Capacity: " + slot.getCapacity()));
        }
    }

    /**
     * Handle generate reports.
     *
     * @param scanner      the scanner
     * @param adminService the admin service
     */
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

    private static void filterGymCenters(Scanner scanner, GymAdminInterface adminService) {
        System.out.println("\n--- Filter Gym Centers ---");
        System.out.println("1. View Approved Gyms");
        System.out.println("2. View Pending (Not Approved) Gyms");
        System.out.println("3. View Gyms by Location");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                adminService.viewApprovedGyms();
                break;
            case 2:
                adminService.viewPendingGyms();
                break;
            case 3:
                System.out.print("Enter city/location: ");
                String location = scanner.nextLine();
                adminService.viewGymsByLocation(location);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
}