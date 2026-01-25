package com.flipfit.client;

import com.flipfit.bean.Gym;
import com.flipfit.business.GymOwnerInterface;
import java.util.List;
import java.util.Scanner;

public class GymOwnerFlipFitMenu {

    public static void showOwnerMenu(Scanner scanner, GymOwnerInterface ownerService) {
        boolean back = false;

        while (!back) {
            System.out.println("\n========================================");
            System.out.println("            OWNER MENU");
            System.out.println("========================================");
            System.out.println("1. Register New Gym");
            System.out.println("2. View My Gyms");
            System.out.println("3. Update Gym Schedule");
            System.out.println("4. View My Bookings");
            System.out.println("5. Logout");
            System.out.println("========================================");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    Gym newGym = new Gym();
                    System.out.print("Enter Gym ID: "); newGym.setGymId(scanner.nextLine());
                    System.out.print("Enter Gym Name: "); newGym.setGymName(scanner.nextLine());
                    System.out.print("Enter Location (City): "); newGym.setLocation(scanner.nextLine());
                    newGym.setApproved(false); // Default to pending
                    ownerService.registerGym(newGym);
                    break;
                case 2:
                    List<Gym> myGyms = ownerService.viewMyGyms();
                    if (myGyms.isEmpty()) {
                        System.out.println("No gyms registered under your account.");
                    } else {
                        System.out.println("\n--- My Gyms ---");
                        myGyms.forEach(g -> System.out.println("ID: " + g.getGymId() + " | Name: " + g.getGymName() + " | Approved: " + g.isApproved()));
                    }
                    break;
                case 3:
                    System.out.print("Enter Gym ID to update: ");
                    ownerService.updateSchedule(scanner.nextLine());
                    break;
                case 4:
                    ownerService.viewBookings();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}