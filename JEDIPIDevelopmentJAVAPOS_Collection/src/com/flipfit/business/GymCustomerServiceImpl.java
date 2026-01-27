// TODO: Auto-generated Javadoc
package com.flipfit.business;

import com.flipfit.bean.User;
import com.flipfit.bean.Gym;
import com.flipfit.bean.Slot;
import com.flipfit.bean.Booking;
import com.flipfit.dao.GymDAO;
import com.flipfit.dao.SlotDAO;
import com.flipfit.exception.BookingFailedException;
import com.flipfit.exception.SlotNotAvailableException;
import com.flipfit.dao.BookingDAO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The Class GymCustomerServiceImpl.
 *
 * @author team pi
 * @ClassName "GymCustomerServiceImpl"
 */
public class GymCustomerServiceImpl implements GymCustomerInterface {

    /** The gym DAO. */
    private GymDAO gymDAO;

    /** The slot DAO. */
    private SlotDAO slotDAO;

    /** The booking DAO. */
    private BookingDAO bookingDAO;

    /**
     * Instantiates a new gym customer service impl.
     */
    public GymCustomerServiceImpl() {
        this.gymDAO = new GymDAO();
        this.slotDAO = new SlotDAO();
        this.bookingDAO = new BookingDAO();
    }

    /**
     * Register.
     *
     * @param user the user
     */
    public void register(User user) {
        GymUserServiceImpl userService = new GymUserServiceImpl();
        userService.register(user);
    }

    /**
     * View centers.
     *
     * @param cityInput the city input
     */
    public void viewCenters(String cityInput) {
        List<Gym> filteredGyms = gymDAO.getGymsByLocation(cityInput);
        if (filteredGyms.isEmpty()) {
            System.out.println("No approved gym centers found in " + cityInput);
        } else {
            System.out.println("\n--- Available Centers in " + cityInput + " ---");
            filteredGyms.forEach(g -> System.out
                    .println("ID: " + g.getGymId() + " | Name: " + g.getGymName() + " | Address: " + g.getLocation()));
        }
    }

    /**
     * View slots for gym.
     *
     * @param gymId the gym id
     * @param date  the date
     */
    public void viewSlotsForGym(String gymId, LocalDate date) {
        List<Slot> slots = slotDAO.getSlotsByGymId(gymId);
        if (slots.isEmpty()) {
            System.out.println("No slots available for this gym.");
        } else {
            System.out.println("\n--- Available Slots for Gym " + gymId + " on " + date + " ---");
            for (Slot slot : slots) {
                String availability = slot.getAvailableSeats() > 0 ? "Available" : "Full";
                System.out.println("Slot ID: " + slot.getSlotId() + " | Time: " + slot.getStartTime() + " - "
                        + slot.getEndTime() + " | Available: " + slot.getAvailableSeats() + "/" + slot.getCapacity()
                        + " | Status: " + availability);
            }
        }
    }

    /**
     * Gets the available slots.
     *
     * @param gymId the gym id
     * @param date  the date
     * @return the available slots
     */
    public List<Slot> getAvailableSlots(String gymId, LocalDate date) {
        return slotDAO.getAvailableSlotsByGymId(gymId);
    }

    /**
     * Book slot.
     *
     * @param userId the user id
     * @param slotId the slot id
     * @param gymId  the gym id
     * @param date   the date
     * @return true, if successful
     * @throws BookingFailedException    the booking failed exception
     * @throws SlotNotAvailableException the slot not available exception
     */
    public boolean bookSlot(String userId, String slotId, String gymId, LocalDate date)
            throws BookingFailedException, SlotNotAvailableException {

        Slot targetSlot = slotDAO.getSlotById(slotId); //
        if (targetSlot == null) {
            throw new SlotNotAvailableException("Error: Slot " + slotId + " does not exist."); //
        }
        if (targetSlot.getAvailableSeats() <= 0) {
            throw new SlotNotAvailableException("Error: Slot " + slotId + " is fully booked!"); //
        }

        // Logic for conflicts
        List<Booking> userBookings = bookingDAO.getBookingsByUserId(userId); //
        boolean conflict = userBookings.stream()
                .filter(b -> b.getBookingDate().equals(date) && !b.getStatus().equals("CANCELLED"))
                .anyMatch(b -> {
                    Slot s = slotDAO.getSlotById(b.getSlotId()); //
                    return s != null && !(targetSlot.getEndTime().isBefore(s.getStartTime()) ||
                            targetSlot.getStartTime().isAfter(s.getEndTime()));
                });

        if (conflict) {
            throw new BookingFailedException("Time conflict: You already have a booking during this period."); //
        }

        Booking newBooking = new Booking();
        newBooking.setBookingId(bookingDAO.generateBookingId()); //
        newBooking.setUserId(userId);
        newBooking.setSlotId(slotId);
        newBooking.setGymId(gymId);
        newBooking.setBookingDate(date);
        newBooking.setStatus("CONFIRMED");
        newBooking.setCreatedAt(LocalDateTime.now());

        if (bookingDAO.saveBooking(newBooking)) { //
            slotDAO.decreaseAvailableSeats(slotId); //
            return true;
        } else {
            throw new BookingFailedException("Database error: Booking could not be saved."); //
        }
    }

    /**
     * Cancel booking.
     *
     * @param bookingId the booking id
     * @param userId    the user id
     * @return true, if successful
     * @throws BookingFailedException the booking failed exception
     */
    public boolean cancelBooking(String bookingId, String userId) throws BookingFailedException {
        Booking booking = bookingDAO.getBookingById(bookingId); //
        if (booking == null || !booking.getUserId().equals(userId)) {
            throw new BookingFailedException("Booking not found or access denied."); //
        }
        if (booking.getStatus().equals("CANCELLED")) {
            throw new BookingFailedException("Booking is already cancelled."); //
        }

        if (bookingDAO.cancelBooking(bookingId)) { //
            slotDAO.increaseAvailableSeats(booking.getSlotId()); //
            return true;
        }
        throw new BookingFailedException("System error: Cancellation failed."); //
    }

    /**
     * View my bookings.
     *
     * @param userId the user id
     */
    public void viewMyBookings(String userId) {
        List<Booking> userBookings = bookingDAO.getBookingsByUserId(userId);
        if (userBookings.isEmpty()) {
            System.out.println("You have no bookings.");
        } else {
            System.out.println("\n--- Your Bookings ---");
            for (Booking booking : userBookings) {
                Slot slot = slotDAO.getSlotById(booking.getSlotId());
                String timeInfo = "";
                if (slot != null) {
                    timeInfo = " | Time: " + slot.getStartTime() + " - " + slot.getEndTime();
                }
                System.out.println("Booking ID: " + booking.getBookingId() + " | Gym: " + booking.getGymId()
                        + " | Date: " + booking.getBookingDate() + timeInfo + " | Status: " + booking.getStatus());
            }
        }
    }
}
