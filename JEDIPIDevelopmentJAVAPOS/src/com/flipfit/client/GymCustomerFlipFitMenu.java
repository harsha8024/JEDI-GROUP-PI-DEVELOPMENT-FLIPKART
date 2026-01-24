package com.flipfit.client;

import com.flipfit.business.GymCustomerInterface;
import java.util.Scanner;

public class GymCustomerFlipFitMenu {

    public static void showCustomerMenu(Scanner scanner, GymCustomerInterface customerService) {
        boolean back = false;

        while (!back) {
            System.out.println("\n========================================");
            System.out.println("            CUSTOMER MENU");
            System.out.println("========================================");
            System.out.println("1. View Centers (by City)");
            System.out.println("2. Book a Slot");
            System.out.println("3. Cancel Booking");
            System.out.println("4. Logout");
            System.out.println("========================================");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter City Name: ");
                    customerService.viewCenters(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("Enter Slot ID to book: ");
                    customerService.bookSlot(scanner.nextLine());
                    break;
                case 3:
                    System.out.print("Enter Booking ID to cancel: ");
                    customerService.cancelBooking(scanner.nextLine());
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}