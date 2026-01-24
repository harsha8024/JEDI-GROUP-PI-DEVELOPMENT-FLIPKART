package com.flipfit.business;

import com.flipfit.bean.User;
import com.flipfit.bean.Gym;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GymCustomerServiceImpl implements GymCustomerInterface {
    // List to store booking records dynamically
    private static List<String> bookingList = new ArrayList<>();

    @Override
    public void register(User user) {
        // Shared logic with GymUserService to add user to the Map
        System.out.println("Customer " + user.getName() + " registered successfully.");
    }

    @Override
    public void viewCenters(String cityInput) {
        // Accessing the shared collection from GymOwnerServiceImpl
        List<Gym> allGyms = GymOwnerServiceImpl.getGymList(); 
        
        // Filtering where location (the city) matches the input
        List<Gym> filteredGyms = allGyms.stream()
            .filter(gym -> gym.getLocation() != null && gym.getLocation().equalsIgnoreCase(cityInput))
            .filter(Gym::isApproved) // Only show gyms vetted by Admin
            .collect(Collectors.toList());

        if (filteredGyms.isEmpty()) {
            System.out.println("No approved gym centers found in " + cityInput);
        } else {
            System.out.println("--- Available Centers in " + cityInput + " ---");
            filteredGyms.forEach(g -> 
                System.out.println("ID: " + g.getGymId() + " | Name: " + g.getGymName() + " | Address: " + g.getLocation())
            );
        }
    }

    @Override
    public boolean bookSlot(String slotId) {
        // Updated logic: in a real flow, you'd fetch the current UserID from a session
        // For now, we add the slotId to our collection
        bookingList.add(slotId); 
        System.out.println("Successfully booked slot: " + slotId);
        return true;
    }

    @Override
    public boolean cancelBooking(String bookingId) {
    	boolean removed = bookingList.removeIf(booking -> booking.contains("Slot:" + bookingId));
        
        if (removed) {
            System.out.println("Success: Booking " + bookingId + " has been removed from the system.");
        } else {
            System.out.println("Error: No booking found with ID " + bookingId);
        }
        
        return removed;
    }
}