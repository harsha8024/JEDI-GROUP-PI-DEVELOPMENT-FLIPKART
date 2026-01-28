// TODO: Auto-generated Javadoc
package com.flipfit.business;

import com.flipfit.bean.Booking;
import java.time.LocalDate;
import java.util.List;

/**
 * The Interface BookingInterface.
 * Defines the contract for booking management operations.
 *
 * @author team pi
 * @ClassName "BookingInterface"
 */
public interface BookingInterface {
    
    /**
     * Check booking overlap.
     * Checks if the user already has a booking on the specified date.
     *
     * @param userId the user id
     * @param date the date
     * @param slotId the slot id
     * @return true, if successful
     */
    boolean checkBookingOverlap(String userId, LocalDate date, String slotId);
    
    /**
     * Adds the booking.
     * Creates a new booking for the specified user, slot, and gym.
     *
     * @param userId the user id
     * @param slotId the slot id
     * @param gymId the gym id
     * @return the string
     */
    String addBooking(String userId, String slotId, String gymId);
    
    /**
     * Cancel booking.
     *
     * @param bookingId the booking id
     * @return true, if successful
     */
    boolean cancelBooking(String bookingId);
    
    /**
     * Gets the booking by id.
     *
     * @param bookingId the booking id
     * @return the booking by id
     */
    Booking getBookingById(String bookingId);
    
    /**
     * Gets the bookings by user id.
     *
     * @param userId the user id
     * @return the bookings by user id
     */
    List<Booking> getBookingsByUserId(String userId);
    
    /**
     * Gets the bookings by gym id.
     *
     * @param gymId the gym id
     * @return the bookings by gym id
     */
    List<Booking> getBookingsByGymId(String gymId);
}