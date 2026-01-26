package com.flipfit.client;

import com.flipfit.bean.Gym;
import com.flipfit.business.GymOwnerInterface;
import com.flipfit.business.GymUserInterface;
import com.flipfit.business.GymUserServiceImpl;
import java.util.List;
import java.util.Scanner;

public class GymOwnerFlipFitMenu {

    public static void showOwnerMenu(Scanner scanner, GymOwnerInterface ownerService,String loggedInEmail) {
        boolean back = false;
        // Accessing userService for profile updates
        GymUserInterface userService = new GymUserServiceImpl();

        while (!back) {
            System.out.println("\n========================================");
            System.out.println("            OWNER MENU");
            System.out.println("========================================");
            System.out.println("1. Register New Gym");
            System.out.println("2. View My Gyms");
            System.out.println("3. Add Slots to Gym"); // New Option
            System.out.println("4. Update Gym Schedule");
            System.out.println("5. View My Bookings");
            System.out.println("6. Update Profile"); // New Option
            System.out.println("7. Logout");
            System.out.println("========================================");
            System.out.print("Enter choice: ");

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
                	System.out.print("Enter Gym ID: ");
                    String gId = scanner.nextLine();
                    System.out.print("Start Time: ");
                    String time = scanner.nextLine();
                    System.out.print("Seats: ");
                    int seats = scanner.nextInt();
                    scanner.nextLine();
                    
                    // Service will handle the ID validation
                    ownerService.addSlot(gId, time, seats);
                    break;
                case 4:
                	System.out.print("Enter Gym ID to modify: ");
                    String Id = scanner.nextLine();
                    ownerService.updateGymDetails(Id); // Logic handled inside service
                    break;
                case 5:
                    ownerService.viewBookings();
                    break;
                case 6:
                    System.out.println("\n--- UPDATE PROFILE ---");
                    System.out.print("Enter New Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter New City: ");
                    String city = scanner.nextLine();
                    
                    // Call service to update the static collection
                    ownerService.updateProfile(loggedInEmail, name, city);
                    break;
                case 7:
                    System.out.println("Logging out...");
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void handleRegisterGym(Scanner scanner, GymOwnerInterface ownerService) {
        Gym newGym = new Gym();
        System.out.print("Enter Gym ID: "); newGym.setGymId(scanner.nextLine());
        System.out.print("Enter Gym Name: "); newGym.setGymName(scanner.nextLine());
        System.out.print("Enter Location (City): "); newGym.setLocation(scanner.nextLine());
        newGym.setApproved(false); 
        ownerService.registerGym(newGym);
    }

    private static void handleViewMyGyms(GymOwnerInterface ownerService) {
        List<Gym> myGyms = ownerService.viewMyGyms();
        if (myGyms.isEmpty()) {
            System.out.println("No gyms registered under your account.");
        } else {
            System.out.println("\n--- My Gyms ---");
            myGyms.forEach(g -> System.out.println("ID: " + g.getGymId() + " | Name: " + g.getGymName() + " | Approved: " + g.isApproved()));
        }
    }

    private static void handleAddSlots(Scanner scanner, GymOwnerInterface ownerService) {
        System.out.println("\n--- Add Slots ---");
        System.out.print("Enter Gym ID: ");
        String gymId = scanner.nextLine();
        System.out.print("Enter Start Time (e.g., 06:00): ");
        String startTime = scanner.nextLine();
        System.out.print("Enter Number of Seats: ");
        int seats = scanner.nextInt();
        scanner.nextLine();

        // Ensure your GymOwnerInterface has a method like addSlot or updateSchedule handles this
        System.out.println("Slot added for " + startTime + " in Gym " + gymId);
    }
}