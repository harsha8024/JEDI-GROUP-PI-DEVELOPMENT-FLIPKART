// TODO: Auto-generated Javadoc
package com.flipfit.client;

import com.flipfit.bean.Gym;
import com.flipfit.business.GymOwnerInterface;
import com.flipfit.business.SlotServiceImpl;
import com.flipfit.exception.RegistrationFailedException;
import com.flipfit.exception.UserNotFoundException;
import com.flipfit.exception.InvalidInputException;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * The Class GymOwnerFlipFitMenu.
 *
 * @author team pi
 * @ClassName "GymOwnerFlipFitMenu"
 */
public class GymOwnerFlipFitMenu {

    /** The current owner id. */
    private static String currentOwnerId = null;

    /**
     * Show owner menu.
     *
     * @param scanner      the scanner
     * @param ownerService the owner service
     */
    public static void showOwnerMenu(Scanner scanner, GymOwnerInterface ownerService) {
        showOwnerMenu(scanner, ownerService, null);
    }

    /**
     * Show owner menu.
     *
     * @param scanner      the scanner
     * @param ownerService the owner service
     * @param ownerId      the owner id
     */
    public static void showOwnerMenu(Scanner scanner, GymOwnerInterface ownerService, String ownerId) {
        currentOwnerId = ownerId;
        boolean back = false;

        while (!back) {
            System.out.println("\n========================================");
            System.out.println("            OWNER MENU");
            System.out.println("========================================");
            System.out.println("1. Register New Gym");
            System.out.println("2. View My Gyms");
            System.out.println("3. Add Slots to Gym");
            System.out.println("4. View Gym Schedule");
            System.out.println("5. View My Bookings");
            System.out.println("6. Logout");
            System.out.println("========================================");
            System.out.print("Enter choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        handleRegisterGym(scanner, ownerService);
                        break;
                    case 2:
                        handleViewMyGyms(ownerService);
                        break;
                    case 3:
                        handleAddSlots(scanner);
                        break;
                    case 4:
                        handleViewSchedule(scanner, ownerService);
                        break;
                    case 5:
                        try {
                            if (currentOwnerId != null) {
                                ownerService.viewBookings(currentOwnerId);
                            } else {
                                ownerService.viewBookings();
                            }
                        } catch (UserNotFoundException e) {
                            System.out.println("\n[ERROR] Could not retrieve bookings: " + e.getMessage());
                        } catch (InvalidInputException e) {
                            System.out.println("\n[INPUT ERROR] " + e.getMessage());
                        }
                        break;
                    case 6:
                        back = true;
                        System.out.println("Logging out...");
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    /**
     * Handle register gym.
     *
     * @param scanner      the scanner
     * @param ownerService the owner service
     */
    private static void handleRegisterGym(Scanner scanner, GymOwnerInterface ownerService) {
        Gym newGym = new Gym();
        System.out.print("Enter Gym Name: ");
        newGym.setGymName(scanner.nextLine());
        System.out.print("Enter Location: ");
        newGym.setLocation(scanner.nextLine());
        newGym.setGymOwnerId(currentOwnerId);

        try {
            ownerService.registerGym(newGym);
            System.out.println("\nGym registered successfully. It will be visible once approved.");
        } catch (RegistrationFailedException e) {
            System.out.println("[REGISTRATION ERROR] " + e.getMessage());
        } catch (InvalidInputException e) {
            System.out.println("[INPUT ERROR] " + e.getMessage());
        }
    }

    /**
     * Handle view my gyms.
     *
     * @param ownerService the owner service
     */
    private static void handleViewMyGyms(GymOwnerInterface ownerService) {
        List<Gym> myGyms;
        try {
            if (currentOwnerId != null) {
                myGyms = ownerService.viewMyGyms(currentOwnerId);
            } else {
                myGyms = ownerService.viewMyGyms();
            }

            if (myGyms.isEmpty()) {
                System.out.println("No gyms registered under your account.");
            } else {
                System.out.println("\n========================================");
                System.out.println("           MY GYMS");
                System.out.println("========================================");
                myGyms.forEach(g -> {
                    String status = g.isApproved() ? "APPROVED" : "PENDING";
                    System.out.println("ID: " + g.getGymId() +
                            " | Name: " + g.getGymName() +
                            " | Location: " + g.getLocation() +
                            " | Status: " + status);
                });
                System.out.println("========================================");
                System.out.println("Total: " + myGyms.size() + " gyms");
            }
        } catch (UserNotFoundException e) {
            System.out.println("\n[ERROR] Could not retrieve gyms: " + e.getMessage());
        } catch (InvalidInputException e) {
            System.out.println("\n[INPUT ERROR] " + e.getMessage());
        }
    }

    /**
     * Handle add slots.
     *
     * @param scanner the scanner
     */
    private static void handleAddSlots(Scanner scanner) {
        SlotServiceImpl slotService = new SlotServiceImpl();

        System.out.print("Enter Gym ID: ");
        String gymId = scanner.nextLine();

        System.out.print("Enter Start Time (HH:mm, e.g., 06:00): ");
        String startStr = scanner.nextLine();

        System.out.print("Enter End Time (HH:mm, e.g., 07:00): ");
        String endStr = scanner.nextLine();

        System.out.print("Enter Capacity: ");
        int capacity = scanner.nextInt();
        scanner.nextLine();

        try {
            LocalTime startTime = LocalTime.parse(startStr, DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime endTime = LocalTime.parse(endStr, DateTimeFormatter.ofPattern("HH:mm"));

            slotService.createSlot(gymId, startTime, endTime, capacity);
            System.out.println("\n✓ Slot created successfully!");
        } catch (java.time.format.DateTimeParseException dtpe) {
            System.out.println("Error: Invalid time format. Use HH:mm format (e.g., 06:00)");
        } catch (RegistrationFailedException | InvalidInputException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    /**
     * Handle view schedule.
     *
     * @param scanner      the scanner
     * @param ownerService the owner service
     */
    private static void handleViewSchedule(Scanner scanner, GymOwnerInterface ownerService) {
        System.out.print("Enter Gym ID: ");
        String gymId = scanner.nextLine();
        try {
            ownerService.updateSchedule(gymId);
        } catch (InvalidInputException e) {
            System.out.println("[INPUT ERROR] " + e.getMessage());
        }
    }
}