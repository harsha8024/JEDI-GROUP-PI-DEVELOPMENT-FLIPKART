package com.flipfit.client;

import java.util.Scanner;

import com.flipfit.business.GymAdminInterface;
import com.flipfit.business.GymAdminServiceImpl;
import com.flipfit.business.GymCustomerInterface;
import com.flipfit.business.GymCustomerServiceImpl;
import com.flipfit.business.GymOwnerInterface;
import com.flipfit.business.GymOwnerServiceImpl;
import com.flipfit.business.GymUserInterface;
import com.flipfit.business.GymUserServiceImpl;


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
		System.out.println("   Welcome to FlipFit Gym Booking System");
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
					case 4:
						handleGuestMenu(scanner, customerService);
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
	
	/**
	 * Display the main menu
	 */
	private static void displayMainMenu() {
		System.out.println("\n========================================");
		System.out.println("            MAIN MENU");
		System.out.println("========================================");
		System.out.println("1. Login");
		System.out.println("2. Register");
		System.out.println("3. Change Password");
		System.out.println("3. Browse as Guest");
		System.out.println("4. Exit");
		System.out.println("========================================");
	}
	
	/**
	 * Handle user login
	 */
	private static void handleLogin(Scanner scanner, GymUserInterface userService,
									GymCustomerInterface customerService, 
									GymOwnerInterface ownerService,
									GymAdminInterface adminService) {
		System.out.println("\n--- Login ---");
		System.out.print("Email: ");
		String email = scanner.nextLine();
		System.out.print("Password: ");
		String password = scanner.nextLine();
		
		// In a real implementation, this would validate credentials
		System.out.println("\nLogin successful!");
		System.out.print("Select your role (1: Customer, 2: Owner, 3: Admin): ");
		int roleChoice = scanner.nextInt();
		scanner.nextLine();
		
		switch (roleChoice) {
			case 1:
				handleCustomerMenu(scanner, customerService);
				break;
			case 2:
				handleOwnerMenu(scanner, ownerService);
				break;
			case 3:
				handleAdminMenu(scanner, adminService);
				break;
			default:
				System.out.println("Invalid role selection!");
		}
	}
	
	/**
	 * Handle user registration
	 */
	private static void handleRegistration(Scanner scanner, GymUserInterface userService) {
		System.out.println("\n--- Registration ---");
		System.out.print("Name: ");
		String name = scanner.nextLine();
		System.out.print("Email: ");
		String email = scanner.nextLine();
		System.out.print("Phone Number: ");
		String phoneNumber = scanner.nextLine();
		System.out.print("City: ");
		String city = scanner.nextLine();
		System.out.print("Password: ");
		String password = scanner.nextLine();
		System.out.print("Role (Customer/Owner/Admin): ");
		String role = scanner.nextLine();
		
		System.out.println("\nRegistration successful! Please login to continue.");
	}
	
	private static void handleChangePassword(Scanner scanner, GymUserInterface userService) {
        System.out.println("\n--- Change Password ---");
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Current Password: ");
        String oldPassword = scanner.nextLine();
        System.out.print("Enter New Password: ");
        String newPassword = scanner.nextLine();
        
        // userService.updatePassword(email, oldPassword, newPassword);
        System.out.println("Password updated successfully!");
    }
	
	/**
	 * Handle guest menu for browsing
	 */
	private static void handleGuestMenu(Scanner scanner, GymCustomerInterface customerService) {
		boolean back = false;
		
		while (!back) {
			System.out.println("\n--- Guest Menu ---");
			System.out.println("1. View All Gyms in City");
			System.out.println("2. View Gym Details");
			System.out.println("3. Back to Main Menu");
			System.out.print("Enter your choice: ");
			
			int choice = scanner.nextInt();
			scanner.nextLine();
			
			switch (choice) {
				case 1:
					System.out.print("Enter city name: ");
					String city = scanner.nextLine();
					System.out.println("\nDisplaying gyms in " + city + "...");
					// Call service method to display gyms
					break;
				case 2:
					System.out.print("Enter gym ID: ");
					String gymId = scanner.nextLine();
					System.out.println("\nDisplaying details for gym " + gymId + "...");
					// Call service method to display gym details
					break;
				case 3:
					back = true;
					break;
				default:
					System.out.println("Invalid choice!");
			}
		}
	}
	
	/**
	 * Handle customer menu
	 */
	private static void handleCustomerMenu(Scanner scanner, GymCustomerInterface customerService) {
		boolean back = false;
		
		while (!back) {
			System.out.println("\n========================================");
			System.out.println("         CUSTOMER MENU");
			System.out.println("========================================");
			System.out.println("1. View All Gyms");
			System.out.println("2. View Available Slots");
			System.out.println("3. Book a Slot");
			System.out.println("4. View My Bookings");
			System.out.println("5. Cancel Booking");
			System.out.println("6. View Plan by Day");
			System.out.println("7. Update Profile");
			System.out.println("8. Logout");
			System.out.println("========================================");
			System.out.print("Enter your choice: ");
			
			int choice = scanner.nextInt();
			scanner.nextLine();
			
			switch (choice) {
				case 1:
					System.out.print("Enter city name: ");
					String city = scanner.nextLine();
					System.out.println("\nFetching gyms in " + city + "...");
					// customerService.viewGyms(city);
					break;
				case 2:
					System.out.print("Enter gym ID: ");
					String gymId = scanner.nextLine();
					System.out.print("Enter date (YYYY-MM-DD): ");
					String date = scanner.nextLine();
					System.out.println("\nFetching available slots...");
					// customerService.viewAvailableSlots(gymId, date);
					break;
				case 3:
					handleBookingProcess(scanner, customerService);
					break;
				case 4:
					System.out.println("\nYour Bookings:");
					// customerService.viewMyBookings(userId);
					break;
				case 5:
					System.out.print("Enter booking ID to cancel: ");
					String bookingId = scanner.nextLine();
					System.out.println("\nBooking " + bookingId + " cancelled successfully!");
					// customerService.cancelBooking(bookingId);
					break;
				case 6:
					System.out.print("Enter date (YYYY-MM-DD): ");
					String planDate = scanner.nextLine();
					System.out.println("\nYour plan for " + planDate + ":");
					// customerService.viewPlanByDay(userId, planDate);
					break;
				case 7:
					GymUserFlipFitMenu.showMenu(scanner);
					break;
				case 8:
					System.out.println("\nLogged out successfully!");
					back = true;
					break;
				default:
					System.out.println("Invalid choice!");
			}
		}
	}
	
	/**
	 * Handle booking process
	 */
	private static void handleBookingProcess(Scanner scanner, GymCustomerInterface customerService) {
		System.out.println("\n--- Book a Slot ---");
		System.out.print("Enter gym ID: ");
		String gymId = scanner.nextLine();
		System.out.print("Enter slot ID: ");
		String slotId = scanner.nextLine();
		System.out.print("Enter date (YYYY-MM-DD): ");
		String date = scanner.nextLine();
		System.out.print("Any special requirements? (or press Enter to skip): ");
		String specialReq = scanner.nextLine();
		
		System.out.println("\nBooking confirmed!");
		System.out.println("You will receive a confirmation email shortly.");
		// customerService.bookSlot(userId, gymId, slotId, date, specialReq);
	}
	
	/**
	 * Handle gym owner menu
	 */
	private static void handleOwnerMenu(Scanner scanner, GymOwnerInterface ownerService) {
		boolean back = false;
		
		while (!back) {
			System.out.println("\n========================================");
			System.out.println("           OWNER MENU");
			System.out.println("========================================");
			System.out.println("1. Add New Gym");
			System.out.println("2. View My Gyms");
			System.out.println("3. Add Slots to Gym");
			System.out.println("4. Update Gym Details");
			System.out.println("5. View Bookings");
			System.out.println("6. Update Profile");
			System.out.println("7. Logout");
			System.out.println("========================================");
			System.out.print("Enter your choice: ");
			
			int choice = scanner.nextInt();
			scanner.nextLine();
			
			switch (choice) {
				case 1:
					System.out.println("\n--- Add New Gym ---");
					System.out.print("Gym Name: ");
					String gymName = scanner.nextLine();
					System.out.print("Location: ");
					String location = scanner.nextLine();
					System.out.print("City: ");
					String city = scanner.nextLine();
					System.out.println("\nGym added! Waiting for admin approval.");
					// ownerService.addGym(ownerId, gymName, location, city);
					break;
				case 2:
					System.out.println("\nYour Gyms:");
					// ownerService.viewMyGyms(ownerId);
					break;
				case 3:
					handleAddSlots(scanner, ownerService);
					break;
				case 4:
					System.out.print("Enter gym ID to update: ");
					String gymId = scanner.nextLine();
					System.out.println("\nUpdating gym " + gymId + "...");
					// ownerService.updateGym(gymId);
					break;
				case 5:
					System.out.print("Enter gym ID: ");
					String gymIdForBookings = scanner.nextLine();
					System.out.println("\nBookings for gym " + gymIdForBookings + ":");
					// ownerService.viewBookings(gymIdForBookings);
					break;
				case 6:
					GymUserFlipFitMenu.showMenu(scanner);
					break;
				case 7:
					System.out.println("\nLogged out successfully!");
					back = true;
					break;
				default:
					System.out.println("Invalid choice!");
			}
		}
	}
	
	/**
	 * Handle adding slots to a gym
	 */
	private static void handleAddSlots(Scanner scanner, GymOwnerInterface ownerService) {
		System.out.println("\n--- Add Slots ---");
		System.out.print("Enter gym ID: ");
		String gymId = scanner.nextLine();
		System.out.print("Start time (HH:MM): ");
		String startTime = scanner.nextLine();
		System.out.print("End time (HH:MM): ");
		String endTime = scanner.nextLine();
		System.out.print("Capacity: ");
		int capacity = scanner.nextInt();
		scanner.nextLine();
		
		System.out.println("\nSlot added successfully!");
		// ownerService.addSlot(gymId, startTime, endTime, capacity);
	}
	
	/**
	 * Handle admin menu
	 */
	private static void handleAdminMenu(Scanner scanner, GymAdminInterface adminService) {
		boolean back = false;
		
		while (!back) {
			System.out.println("\n========================================");
			System.out.println("           ADMIN MENU");
			System.out.println("========================================");
			System.out.println("1. View Pending Gym Approvals");
			System.out.println("2. Approve Gym");
			System.out.println("3. Reject Gym");
			System.out.println("4. View All Gyms");
			System.out.println("5. View All Users");
			System.out.println("6. View All Bookings");
			System.out.println("7. Generate Reports");
			System.out.println("8. Logout");
			System.out.println("========================================");
			System.out.print("Enter your choice: ");
			
			int choice = scanner.nextInt();
			scanner.nextLine();
			
			switch (choice) {
				case 1:
					System.out.println("\nPending Gym Approvals:");
					// adminService.viewPendingApprovals();
					break;
				case 2:
					System.out.print("Enter gym ID to approve: ");
					String approveGymId = scanner.nextLine();
					System.out.println("\nGym " + approveGymId + " approved!");
					// adminService.approveGym(approveGymId);
					break;
				case 3:
					System.out.print("Enter gym ID to reject: ");
					String rejectGymId = scanner.nextLine();
					System.out.print("Reason for rejection: ");
					String reason = scanner.nextLine();
					System.out.println("\nGym " + rejectGymId + " rejected.");
					// adminService.rejectGym(rejectGymId, reason);
					break;
				case 4:
					System.out.println("\nAll Registered Gyms:");
					// adminService.viewAllGyms();
					break;
				case 5:
					System.out.println("\nAll Registered Users:");
					// adminService.viewAllUsers();
					break;
				case 6:
					System.out.println("\nAll Bookings:");
					// adminService.viewAllBookings();
					break;
				case 7:
					System.out.println("\nGenerating reports...");
					System.out.println("1. Daily Booking Report");
					System.out.println("2. Gym Utilization Report");
					System.out.println("3. Revenue Report");
					// adminService.generateReports();
					break;
				case 8:
					System.out.println("\nLogged out successfully!");
					back = true;
					break;
				default:
					System.out.println("Invalid choice!");
			}
		}
	}
}
