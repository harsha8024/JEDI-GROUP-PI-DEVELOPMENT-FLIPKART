package com.flipfit.client;

import com.flipfit.business.GymCustomerInterface;
import com.flipfit.exception.BookingFailedException;
import com.flipfit.exception.SlotNotAvailableException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class GymCustomerFlipFitMenu {
    
    private static String currentUserId = null;

    public static void showCustomerMenu(Scanner scanner, GymCustomerInterface customerService) {
        showCustomerMenu(scanner, customerService, null);
    }

    public static void showCustomerMenu(Scanner scanner, GymCustomerInterface customerService, String userId) {
        currentUserId = userId;
        boolean back = false;

        while (!back) {
            System.out.println("\n========================================");
            System.out.println("            CUSTOMER MENU");
            System.out.println("========================================");
            System.out.println("1. View Centers (by City)");
            System.out.println("2. View Slots for Gym");
            System.out.println("3. Book a Slot");
            System.out.println("4. View My Bookings");
            System.out.println("5. Cancel Booking");
            System.out.println("6. Logout");
            System.out.println("========================================");
            System.out.print("Enter choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter City Name: ");
                        customerService.viewCenters(scanner.nextLine());
                        break;
                    case 2:
                        handleViewSlots(scanner, customerService);
                        break;
                    case 3:
                        handleBookSlot(scanner, customerService);
                        break;
                    case 4:
                        if (currentUserId != null) {
                            customerService.viewMyBookings(currentUserId);
                        } else {
                            System.out.println("Please login first to view bookings.");
                        }
                        break;
                    case 5:
                        handleCancelBooking(scanner, customerService);
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
    
    private static void handleViewSlots(Scanner scanner, GymCustomerInterface customerService) {
        System.out.print("Enter Gym ID: ");
        String gymId = scanner.nextLine();
        System.out.print("Enter Date (yyyy-MM-dd) or press Enter for today: ");
        String dateStr = scanner.nextLine();
        
        LocalDate date;
        if (dateStr.trim().isEmpty()) {
            date = LocalDate.now();
        } else {
            try {
                date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (Exception e) {
                System.out.println("Invalid date format. Using today's date.");
                date = LocalDate.now();
            }
        }
        
        customerService.viewSlotsForGym(gymId, date);
    }
    
    private static void handleBookSlot(Scanner scanner, GymCustomerInterface customerService) {
        if (currentUserId == null) {
            System.out.println("Please login first to book a slot.");
            return;
        }
        
        System.out.print("Enter Gym ID: ");
        String gymId = scanner.nextLine();
        System.out.print("Enter Slot ID: ");
        String slotId = scanner.nextLine();
        System.out.print("Enter Date (yyyy-MM-dd) or press Enter for today: ");
        String dateStr = scanner.nextLine();
        
        LocalDate date;
        if (dateStr.trim().isEmpty()) {
            date = LocalDate.now();
        } else {
            try {
                date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (Exception e) {
                System.out.println("Invalid date format. Using today's date.");
                date = LocalDate.now();
            }
        }
        
        try {
            boolean success = customerService.bookSlot(currentUserId, slotId, gymId, date);
            if (success) {
                System.out.println("\n✓ Slot booked successfully!");
            }
        } catch (BookingFailedException | SlotNotAvailableException e) {
            System.out.println("\n[BOOKING FAILED] " + e.getMessage());
        }
    }

    private static void handleCancelBooking(Scanner scanner, GymCustomerInterface customerService) {
        if (currentUserId == null) {
            System.out.println("Please login first to cancel a booking.");
            return;
        }
        
        System.out.print("Enter Booking ID to cancel: ");
        String bookingId = scanner.nextLine();
        
        try {
            boolean success = customerService.cancelBooking(bookingId, currentUserId);
            if (success) {
                System.out.println("\n✓ Booking cancelled successfully.");
            }
        } catch (BookingFailedException e) {
            System.out.println("\n[CANCELLATION FAILED] " + e.getMessage());
        }
    }
}