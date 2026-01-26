package com.flipfit.client;

import java.util.Scanner;
import java.util.List;              
import com.flipfit.bean.Gym;
import com.flipfit.business.*;

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
						break;
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
		System.out.println("4. Browse as Guest");
		System.out.println("5. Exit");
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
			
			// Check the Collection-based logic in Service
			boolean isAuthenticated = userService.login(email, password);
			
			if (isAuthenticated) {
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
				} else {
					System.out.println("\nLogin failed! Invalid Email or Password.");
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
	    System.out.print("Password: ");
	    String password = scanner.nextLine();
	    System.out.print("Phone Number: ");
	    String phoneNumber = scanner.nextLine();
	    System.out.print("City: ");
	    String city = scanner.nextLine();

	    // 1. Create a User Bean object
	    com.flipfit.bean.User newUser = new com.flipfit.bean.User();
	    newUser.setName(name);
	    newUser.setEmail(email);
	    newUser.setPassword(password);
	    newUser.setPhoneNumber(phoneNumber);
	    newUser.setCity(city);

	    // 2. Pass the bean to the Service to store in the Map
	    // We cast to the Impl to access the register method
	    ((com.flipfit.business.GymUserServiceImpl) userService).register(newUser);

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
	    
	    // Call the Service logic we just wrote
	    // We cast it to the Impl class to access the specific updatePassword method
	    boolean isUpdated = ((GymUserServiceImpl) userService).updatePassword(email, oldPassword, newPassword);
	    
	    if (isUpdated) {
	        System.out.println("Password updated successfully!");
	    } else {
	        System.out.println("Failed to update password. Please check your credentials.");
	    }
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
	        scanner.nextLine(); // Consume newline
	        
	        switch (choice) {
	            case 1:
	                System.out.print("Enter city (location) name: ");
	                String locationInput = scanner.nextLine();
	                // Reusing the logic already written in Customer Service
	                customerService.viewCenters(locationInput);
	                break;
	                
	            case 2:
	                System.out.print("Enter gym ID: ");
	                String inputId = scanner.nextLine().trim(); // .trim() removes accidental spaces
	                System.out.println("\n--- Fetching details for ID: " + inputId + " ---");
	                
	                // Check if the list actually has data
	                List<Gym> currentGyms = GymOwnerServiceImpl.getGymList();
	                
	                currentGyms.stream()
	                    .filter(g -> g.getGymId().equalsIgnoreCase(inputId))
	                    .findFirst()
	                    .ifPresentOrElse(
	                        g -> {
	                            System.out.println("----------------------------");
	                            System.out.println("Gym Name : " + g.getGymName());
	                            System.out.println("Location : " + g.getLocation());
	                            System.out.println("Status   : " + (g.isApproved() ? "Verified" : "Pending Approval"));
	                            System.out.println("----------------------------");
	                        },
	                        () -> System.out.println("[Error] Gym ID " + inputId + " not found in our records.")
	                    );
	                break;
	            case 3:
	                back = true;
	                break;
	                
	            default:
	                System.out.println("Invalid choice! Please select 1, 2, or 3.");
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
				// Inside handleCustomerMenu switch(choice)
				case 1:
				    System.out.print("Enter city name: ");
				    String cityName = scanner.nextLine();
				    customerService.viewCenters(cityName); // Logic linked!
				    break;
				case 2:
				    System.out.print("Enter gym ID: ");
				    String gid = scanner.nextLine();
				    System.out.print("Enter date (YYYY-MM-DD): ");
				    String d = scanner.nextLine();
				    customerService.viewAvailableSlots(gid, d); // Logic linked!
				    break;
				case 3:
				    handleBookingProcess(scanner, customerService); 
				    break;
				case 4:
					customerService.viewMyBookings(); 
				    break;
				case 5:
					System.out.print("Enter booking ID to cancel: ");
				    String bid = scanner.nextLine();
				    
				    // Call the service and check the result
				    boolean isCancelled = customerService.cancelBooking(bid);
				    
				    if (isCancelled) {
				        // Success message is handled inside the service or here
				        System.out.println("\nUpdate: Your schedule has been cleared for that slot.");
				    } else {
				        System.out.println("\nAction Failed: Please verify the Booking ID from 'View My Bookings'.");
				    }
				    break;
				case 6:
				    System.out.print("Enter date (YYYY-MM-DD): ");
				    String planDate = scanner.nextLine();
				    
				    // Calling the service method linked to the Collection
				    customerService.viewPlanByDay(planDate);
				    break;
				    
				case 7:
				    System.out.println("\n--- Update Profile ---");
				    System.out.print("Verify your Email: ");
				    String emailToUpdate = scanner.nextLine();
				    System.out.print("Enter New Name: ");
				    String newName = scanner.nextLine();
				    System.out.print("Enter New Phone: ");
				    String newPhone = scanner.nextLine();
				    System.out.print("Enter New City: ");
				    String newCity = scanner.nextLine();

				    // Call the Service logic
				 // This will no longer be red
				    boolean success = customerService.updateProfile(emailToUpdate, newName, newPhone, newCity);

				    if (success) {
				        System.out.println("Profile updated successfully in the system!");
				    } else {
				        System.out.println("Update failed. User email not found.");
				    }
				    break;  
				case 8:
				    // 1. Call the service to perform logic
					customerService.logout(); 
				    
				    // 2. Break the menu loop to return to Main Menu
				    back = true; 
				    
				    // 3. Provide clear user feedback
				    System.out.println("You have been safely logged out. See you soon!");
				    break;
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
	    
	    // The link: Passing data to the service logic
	    // Based on your current GymCustomerServiceImpl, we pass the slotId
	    boolean isBooked = customerService.bookSlot(slotId);
	    
	    if (isBooked) {
	        System.out.println("\nBooking confirmed for Slot ID: " + slotId);
	        System.out.println("You can verify this in 'View My Bookings'.");
	    } else {
	        System.out.println("\nBooking failed. Slot might be full or invalid.");
	    }
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