package com.flipfit.business;

import com.flipfit.bean.Booking;
import java.time.LocalDate;
import java.util.List;
import com.flipfit.exception.InvalidInputException;
import com.flipfit.exception.BookingCreationException;
import com.flipfit.exception.BookingCancellationException;
import com.flipfit.exception.BookingNotFoundException;

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
     * @throws InvalidInputException if the input parameters are invalid
     */
    boolean checkBookingOverlap(String userId, LocalDate date, String slotId) throws InvalidInputException;

    /**
     * Adds the booking.
     * Creates a new booking for the specified user, slot, and gym.
     *
     * @param userId the user id
     * @param slotId the slot id
     * @param gymId the gym id
     * @return the booking ID string
     * @throws InvalidInputException if inputs are malformed
     * @throws BookingCreationException if the booking process fails
     */
    String addBooking(String userId, String slotId, String gymId) throws InvalidInputException, BookingCreationException;

    /**
     * Cancel booking.
     *
     * @param bookingId the booking id
     * @return true, if successful
     * @throws InvalidInputException if the ID is null or empty
     * @throws BookingCancellationException if the cancellation cannot be completed
     */
    boolean cancelBooking(String bookingId) throws InvalidInputException, BookingCancellationException;

    /**
     * Gets the booking by id.
     *
     * @param bookingId the booking id
     * @return the booking by id
     * @throws InvalidInputException if the ID is invalid
     * @throws BookingNotFoundException if no booking exists with that ID
     */
    Booking getBookingById(String bookingId) throws InvalidInputException, BookingNotFoundException;

    /**
     * Gets the bookings by user id.
     *
     * @param userId the user id
     * @return the bookings by user id
     * @throws InvalidInputException if the user ID is invalid
     */
    List<Booking> getBookingsByUserId(String userId) throws InvalidInputException;

    /**
     * Gets the bookings by gym id.
     *
     * @param gymId the gym id
     * @return the bookings by gym id
     * @throws InvalidInputException if the gym ID is invalid
     */
    List<Booking> getBookingsByGymId(String gymId) throws InvalidInputException;
}