// TODO: Auto-generated Javadoc
package com.flipfit.business;

import com.flipfit.bean.Gym;
import com.flipfit.bean.Booking;
import com.flipfit.dao.GymDAO;
import com.flipfit.dao.BookingDAO;
import com.flipfit.dao.SlotDAO;
import com.flipfit.exception.RegistrationFailedException;
import com.flipfit.exception.UserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The Class GymOwnerServiceImpl.
 *
 * @author team pi
 * @ClassName "GymOwnerServiceImpl"
 */
public class GymOwnerServiceImpl implements GymOwnerInterface {

    /** The gym DAO. */
    private GymDAO gymDAO;

    /** The booking DAO. */
    private BookingDAO bookingDAO;

    /** The slot DAO. */
    private SlotDAO slotDAO;

    /**
     * Instantiates a new gym owner service impl.
     */
    public GymOwnerServiceImpl() {
        this.gymDAO = new GymDAO();
        this.bookingDAO = new BookingDAO();
        this.slotDAO = new SlotDAO();
    }

    /**
     * Gets the gym list.
     *
     * @return the gym list
     */
    // Static getter for Admin service access - using DAO
    public static List<Gym> getGymList() {
        return new GymDAO().getAllGyms();
    }

    /**
     * Register gym.
     *
     * @param gym the gym
     * @throws RegistrationFailedException the registration failed exception
     */
    @Override
    public void registerGym(Gym gym) throws RegistrationFailedException, com.flipfit.exception.InvalidInputException {
        if (gym == null || gym.getGymName() == null || gym.getGymName().isBlank()) {
            throw new com.flipfit.exception.InvalidInputException("Gym information is invalid.");
        }
        String gymId = gymDAO.generateGymId();
        gym.setGymId(gymId);
        gym.setApproved(false);

        if (gymDAO.saveGym(gym)) {
            System.out.println("Gym: " + gym.getGymName() + " registered with ID: " + gymId + " and pending approval.");
        } else {
            throw new RegistrationFailedException("Error: Failed to register gym " + gym.getGymName());
        }
    }

    /**
     * View my gyms.
     *
     * @return the list
     */
    @Override
    public List<Gym> viewMyGyms() {
        return gymDAO.getAllGyms();
    }

    /**
     * View my gyms.
     *
     * @param ownerId the owner id
     * @return the list
     * @throws UserNotFoundException the user not found exception
     */
    @Override
    public List<Gym> viewMyGyms(String ownerId) throws com.flipfit.exception.UserNotFoundException, com.flipfit.exception.InvalidInputException {
        if (ownerId == null || ownerId.isBlank()) throw new com.flipfit.exception.InvalidInputException("Owner ID must be provided.");
        List<Gym> gyms = gymDAO.getGymsByOwnerId(ownerId);
        if (gyms == null)
            throw new com.flipfit.exception.UserNotFoundException("Owner ID not found.");
        return gyms;
    }

    /**
     * View bookings.
     */
    @Override
    public void viewBookings() {
        List<Booking> bookings = bookingDAO.getAllBookings();
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            displayBookings(bookings);
        }
    }

    /**
     * View bookings.
     *
     * @param ownerId the owner id
     */
    @Override
    public void viewBookings(String ownerId) throws com.flipfit.exception.InvalidInputException {
        if (ownerId == null || ownerId.isBlank()) throw new com.flipfit.exception.InvalidInputException("Owner ID must be provided.");
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

    /**
     * Display bookings.
     *
     * @param bookings the bookings
     */
    private void displayBookings(List<Booking> bookings) {
        System.out.println("\n--- Bookings for Your Gyms ---");
        bookings.forEach(booking ->
                System.out.println("Booking ID: " + booking.getBookingId() +
                        " | User: " + booking.getUserId() +
                        " | Gym: " + booking.getGymId() +
                        " | Slot: " + booking.getSlotId() +
                        " | Date: " + booking.getBookingDate() +
                        " | Status: " + booking.getStatus())
        );
    }

    /**
     * Update schedule.
     *
     * @param gymId the gym id
     */
    @Override
    public void updateSchedule(String gymId) throws com.flipfit.exception.InvalidInputException {
        if (gymId == null || gymId.isBlank()) throw new com.flipfit.exception.InvalidInputException("Gym ID must be provided.");
        System.out.println("Updating schedule for Gym ID: " + gymId);
        List<com.flipfit.bean.Slot> slots = slotDAO.getSlotsByGymId(gymId);

        if (slots.isEmpty()) {
            System.out.println("No slots found for this gym.");
        } else {
            System.out.println("Current slots:");
            slots.forEach(slot -> {
                String approvalStatus = slot.isApproved() ? "✓ Approved" : "✗ Pending Approval";
                System.out.println("Slot ID: " + slot.getSlotId() + " | " + slot.getStartTime() + " - "
                        + slot.getEndTime() + " | Capacity: " + slot.getCapacity() + " | Status: " + approvalStatus);
            });
        }
    }
}