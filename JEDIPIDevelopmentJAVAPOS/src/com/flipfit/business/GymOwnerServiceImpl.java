package com.flipfit.business;

import com.flipfit.bean.Gym;
import java.util.ArrayList;
import java.util.*;
import com.flipfit.bean.User;

public class GymOwnerServiceImpl implements GymOwnerInterface {
    // Shared collection for all gym data
    private static List<Gym> gymList = new ArrayList<>();

    // CRITICAL: This method allows Admin and Customer to access the collection
    public static List<Gym> getGymList() {
        return gymList;
    }
    
    @Override
    public void addSlot(String gymId, String startTime, int seats) {
        boolean gymFound = false;

        for (Gym gym : gymList) {
            if (gym.getGymId().equals(gymId)) {
                gymFound = true;
                gym.getSlots().add(startTime + " [" + seats + " seats]");
                System.out.println("[SUCCESS] Slot added to " + gym.getGymName());
                break;
            }
        }

        if (!gymFound) {
            System.out.println("[ERROR] Cannot add slot. Gym ID " + gymId + " does not exist.");
        }
    }
    
    @Override
    public void updateProfile(String email, String newName, String newCity) {
        // Accessing the central user database from the User Service
        Map<String, User> userDatabase = GymUserServiceImpl.getUserMap();
        
        if (userDatabase.containsKey(email)) {
            User owner = userDatabase.get(email);
            owner.setName(newName);
            owner.setCity(newCity);
            // Updating the map with the modified object
            userDatabase.put(email, owner);
            System.out.println("\n[SUCCESS] Profile updated for Owner: " + email);
        } else {
            System.out.println("\n[ERROR] Owner record not found in the system.");
        }
    }

    @Override
    public void registerGym(Gym gym) {
        gymList.add(gym);
        System.out.println("Gym: " + gym.getGymName() + " registered and pending approval.");
    }
    
    @Override
    public List<Gym> viewMyGyms() {
        return gymList; 
    }

    @Override
    public void viewBookings() {
        System.out.println("Displaying bookings for your gyms...");
    }
    
    @Override
    public void updateGymDetails(String gymId) {
        Scanner sc = new Scanner(System.in);
        boolean gymFound = false;

        for (Gym gym : gymList) {
            if (gym.getGymId().equals(gymId)) {
                gymFound = true;
                System.out.println("\n--- Editing Gym: " + gym.getGymName() + " ---");
                System.out.println("1. Update Name/Location");
                System.out.println("2. Update Existing Slot Seats");
                System.out.println("3. Delete a Slot"); // New Option
                System.out.print("Choice: ");
                int choice = sc.nextInt();
                sc.nextLine(); 

                switch (choice) {
                    case 1:
                        System.out.print("Enter New Name: ");
                        gym.setGymName(sc.nextLine());
                        System.out.print("Enter New Location: ");
                        gym.setLocation(sc.nextLine());
                        System.out.println("[SUCCESS] Gym metadata updated.");
                        break;
                    case 2:
                        updateExistingSlot(gym, sc);
                        break;
                    case 3:
                        deleteExistingSlot(gym, sc);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
                return;
            }
        }

        if (!gymFound) {
            System.err.println("[ERROR] No gym found with ID: " + gymId);
        }
    }
    
    private void updateExistingSlot(Gym gym, Scanner sc) {
        if (gym.getSlots().isEmpty()) {
            System.out.println("[ERROR] This gym has no slots to update. Use 'Add Slot' first.");
            return;
        }

        System.out.println("Current Slots: " + gym.getSlots());
        System.out.print("Enter the Slot Time to modify (e.g., 9AM): ");
        String timeToFind = sc.nextLine();

        boolean slotUpdated = false;
        List<String> currentSlots = gym.getSlots();

        for (int i = 0; i < currentSlots.size(); i++) {
            if (currentSlots.get(i).contains(timeToFind)) {
                System.out.print("Enter New Seat Capacity for " + timeToFind + ": ");
                int newSeats = sc.nextInt();
                sc.nextLine();
                
                // Replaces the string at the specific index
                currentSlots.set(i, timeToFind + " [" + newSeats + " seats]");
                slotUpdated = true;
                System.out.println("[SUCCESS] Slot seats updated.");
                break;
            }
        }

        if (!slotUpdated) {
            System.err.println("[ERROR] Slot time '" + timeToFind + "' not found for this gym.");
        }
    }

    private void deleteExistingSlot(Gym gym, Scanner sc) {
        if (gym.getSlots().isEmpty()) {
            System.out.println("[ERROR] No slots to delete.");
            return;
        }

        System.out.println("Current Slots: " + gym.getSlots());
        System.out.print("Enter Slot Time to REMOVE (e.g., 9AM): ");
        String timeToRemove = sc.nextLine();

        // removeIf returns true if an element was actually removed
        boolean removed = gym.getSlots().removeIf(slot -> slot.contains(timeToRemove));

        if (removed) {
            System.out.println("[SUCCESS] Slot " + timeToRemove + " has been deleted.");
        } else {
            System.err.println("[ERROR] Slot time not found.");
        }
    }
    
}