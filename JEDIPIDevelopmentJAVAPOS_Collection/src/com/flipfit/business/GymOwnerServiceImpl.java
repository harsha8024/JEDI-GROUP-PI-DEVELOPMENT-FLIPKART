package com.flipfit.business;

import com.flipfit.bean.Gym;
import com.flipfit.bean.Booking;
import com.flipfit.dao.GymDAO;
import com.flipfit.dao.BookingDAO;
import com.flipfit.dao.SlotDAO;
import java.util.List;
import java.util.stream.Collectors;

public class GymOwnerServiceImpl implements GymOwnerInterface {

    private GymDAO gymDAO;
    private BookingDAO bookingDAO;
    private SlotDAO slotDAO;

    public GymOwnerServiceImpl() {
        this.gymDAO = new GymDAO();
        this.bookingDAO = new BookingDAO();
        this.slotDAO = new SlotDAO();
    }

    // Static getter for Admin service access - using DAO
    public static List<Gym> getGymList() {
        return new GymDAO().getAllGyms();
    }

    @Override
    public void registerGym(Gym gym) {
        String gymId = gymDAO.generateGymId();
        gym.setGymId(gymId);
        gym.setApproved(false);

        if (gymDAO.saveGym(gym)) {
            System.out.println("Gym: " + gym.getGymName() + " registered with ID: " + gymId + " and pending approval.");
        } else {
            System.err.println("Gym registration failed.");
        }
    }

    @Override
    public List<Gym> viewMyGyms() {
        return gymDAO.getAllGyms();
    }

    @Override
    public List<Gym> viewMyGyms(String ownerId) {
        return gymDAO.getGymsByOwnerId(ownerId);
    }

    @Override
    public void viewBookings() {
        List<Booking> bookings = bookingDAO.getAllBookings();
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            displayBookings(bookings);
        }
    }

    @Override
    public void viewBookings(String ownerId) {
        List<Gym> ownerGyms = gymDAO.getGymsByOwnerId(ownerId);
        List<String> ownerGymIds = ownerGyms.stream().map(Gym::getGymId).collect(Collectors.toList());

        List<Booking> allBookings = bookingDAO.getAllBookings();
        List<Booking> ownerBookings = allBookings.stream()
                .filter(booking -> ownerGymIds.contains(booking.getGymId()))
                .collect(Collectors.toList());

        if (ownerBookings.isEmpty()) {
            System.out.println("No bookings found for your gyms.");
        } else {
            displayBookings(ownerBookings);
        }
    }

    private void displayBookings(List<Booking> bookings) {
        System.out.println("\n--- Bookings for Your Gyms ---");
        for (Booking booking : bookings) {
            System.out.println("Booking ID: " + booking.getBookingId() +
                    " | User: " + booking.getUserId() +
                    " | Gym: " + booking.getGymId() +
                    " | Slot: " + booking.getSlotId() +
                    " | Date: " + booking.getBookingDate() +
                    " | Status: " + booking.getStatus());
        }
    }

    @Override
    public void updateSchedule(String gymId) {
        System.out.println("Updating schedule for Gym ID: " + gymId);
        List<com.flipfit.bean.Slot> slots = slotDAO.getSlotsByGymId(gymId);

        if (slots.isEmpty()) {
            System.out.println("No slots found for this gym.");
        } else {
            System.out.println("Current slots:");
            for (com.flipfit.bean.Slot slot : slots) {
                System.out.println("Slot ID: " + slot.getSlotId() + " | " + slot.getStartTime() + " - "
                        + slot.getEndTime() + " | Capacity: " + slot.getCapacity());
            }
        }
    }
}