package com.flipfit.business;

import com.flipfit.bean.Gym;
import com.flipfit.bean.Booking;
import com.flipfit.database.LocalFileDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GymOwnerServiceImpl implements GymOwnerInterface {
    private static List<Gym> gymList = new ArrayList<>();

    static {
        gymList = LocalFileDatabase.loadGyms();
    }

    public static List<Gym> getGymList() {
        return gymList;
    }

    @Override
    public void registerGym(Gym gym) {
        String gymId = LocalFileDatabase.generateGymId();
        gym.setGymId(gymId);
        gym.setApproved(false);
        gymList.add(gym);
        LocalFileDatabase.saveGym(gym);
        System.out.println("Gym: " + gym.getGymName() + " registered with ID: " + gymId + " and pending approval.");
    }
    
    @Override
    public List<Gym> viewMyGyms() {
        return gymList; 
    }
    
    @Override
    public List<Gym> viewMyGyms(String ownerId) {
        return gymList.stream()
            .filter(gym -> gym.getGymOwnerId() != null && gym.getGymOwnerId().equals(ownerId))
            .collect(Collectors.toList());
    }

    @Override
    public void viewBookings() {
        List<Booking> bookings = LocalFileDatabase.loadBookings();
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            System.out.println("\n--- All Bookings for Your Gyms ---");
            for (Booking booking : bookings) {
                System.out.println("Booking ID: " + booking.getBookingId() + 
                                   " | User: " + booking.getUserId() + 
                                   " | Gym: " + booking.getGymId() + 
                                   " | Slot: " + booking.getSlotId() + 
                                   " | Date: " + booking.getBookingDate() + 
                                   " | Approval Status: " + booking.getStatus());
            }
        }
    }
    
    @Override
    public void viewBookings(String ownerId) {
        List<Booking> allBookings = LocalFileDatabase.loadBookings();
        List<String> ownerGymIds = gymList.stream()
            .filter(gym -> gym.getGymOwnerId() != null && gym.getGymOwnerId().equals(ownerId))
            .map(Gym::getGymId)
            .collect(Collectors.toList());
        
        List<Booking> ownerBookings = allBookings.stream()
            .filter(booking -> ownerGymIds.contains(booking.getGymId()))
            .collect(Collectors.toList());
        
        if (ownerBookings.isEmpty()) {
            System.out.println("No bookings found for your gyms.");
        } else {
            System.out.println("\n--- Bookings for Your Gyms ---");
            for (Booking booking : ownerBookings) {
                System.out.println("Booking ID: " + booking.getBookingId() + 
                                   " | User: " + booking.getUserId() + 
                                   " | Gym: " + booking.getGymId() + 
                                   " | Slot: " + booking.getSlotId() + 
                                   " | Date: " + booking.getBookingDate() + 
                                   " | Status: " + booking.getStatus());
            }
        }
    }

    @Override
    public void updateSchedule(String gymId) {
        System.out.println("Updating schedule for Gym ID: " + gymId);
        List<com.flipfit.bean.Slot> slots = LocalFileDatabase.loadSlots().stream()
            .filter(slot -> slot.getGymId().equals(gymId))
            .collect(Collectors.toList());
        
        if (slots.isEmpty()) {
            System.out.println("No slots found for this gym.");
        } else {
            System.out.println("Current slots:");
            for (com.flipfit.bean.Slot slot : slots) {
                System.out.println(slot);
            }
        }
    }
}