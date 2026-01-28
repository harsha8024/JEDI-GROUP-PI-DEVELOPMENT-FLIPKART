// TODO: Auto-generated Javadoc
package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.dao.BookingDAO;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * The Class BookingServiceImpl.
 * Implementation of BookingInterface that provides booking management functionality.
 *
 * @author team pi
 * @ClassName "BookingServiceImpl"
 */
public class BookingServiceImpl implements BookingInterface {

    /** The booking DAO. */
    private final BookingDAO bookingDAO = new BookingDAO();

    /**
     * Check booking overlap.
     * Checks if the user already has an active booking on the specified date.
     *
     * @param userId the user id
     * @param date the date
     * @param slotId the slot id
     * @return true, if a booking overlap exists
     */
    @Override
    public boolean checkBookingOverlap(String userId, LocalDate date, String slotId) {
        // Logic to check if user already has a booking at this time
        List<Booking> userBookings = bookingDAO.getBookingsByUserId(userId);
        
        // Check for any active booking on the same date
        return userBookings.stream()
                .anyMatch(b -> b.getBookingDate().equals(date) && 
                               !"CANCELLED".equalsIgnoreCase(b.getStatus()));
    }

    /**
     * Adds a new booking.
     * Creates a booking record for the specified user, slot, and gym.
     *
     * @param userId the user id
     * @param slotId the slot id
     * @param gymId the gym id
     * @return the booking id if successful, null otherwise
     */
    @Override
    public String addBooking(String userId, String slotId, String gymId) {
        // Use your DAO's ID generator if it exists, otherwise use UUID
        String bookingId = UUID.randomUUID().toString(); 
        
        Booking newBooking = new Booking();
        newBooking.setBookingId(bookingId);
        newBooking.setUserId(userId);
        newBooking.setSlotId(slotId);
        newBooking.setGymId(gymId);
        newBooking.setBookingDate(LocalDate.now()); 
        newBooking.setStatus("CONFIRMED");
        newBooking.setCreatedAt(LocalDateTime.now());
        
        if(bookingDAO.saveBooking(newBooking)) {
            return bookingId;
        }
        return null;
    }

    /**
     * Cancel booking.
     * Cancels the booking with the specified booking id.
     *
     * @param bookingId the booking id
     * @return true, if successful
     */
    @Override
    public boolean cancelBooking(String bookingId) {
        return bookingDAO.cancelBooking(bookingId);
    }

    /**
     * Gets the booking by id.
     *
     * @param bookingId the booking id
     * @return the booking by id
     */
    @Override
    public Booking getBookingById(String bookingId) {
        return bookingDAO.getBookingById(bookingId);
    }

    /**
     * Gets the bookings by user id.
     *
     * @param userId the user id
     * @return the bookings by user id
     */
    @Override
    public List<Booking> getBookingsByUserId(String userId) {
        return bookingDAO.getBookingsByUserId(userId);
    }
    
    /**
     * Gets the bookings by gym id.
     *
     * @param gymId the gym id
     * @return the bookings by gym id
     */
    @Override
    public List<Booking> getBookingsByGymId(String gymId) {
        return bookingDAO.getBookingsByGymId(gymId);
    }
}