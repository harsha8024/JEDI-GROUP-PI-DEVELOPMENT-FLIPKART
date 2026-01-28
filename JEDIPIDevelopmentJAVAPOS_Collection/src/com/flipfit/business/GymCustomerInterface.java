// TODO: Auto-generated Javadoc
package com.flipfit.business;

import com.flipfit.bean.Slot;
import com.flipfit.exception.BookingFailedException;
import com.flipfit.exception.SlotNotAvailableException;
import com.flipfit.exception.InvalidInputException;
import com.flipfit.exception.GymNotFoundException;
import com.flipfit.exception.BookingCreationException;
import java.time.LocalDate;
import java.util.List;

/**
 * The Interface GymCustomerInterface.
 *
 * @author team pi
 * @ClassName "GymCustomerInterface"
 */
public interface GymCustomerInterface {

    /**
     * View centers.
     *
     * @param city the city
     */
    void viewCenters(String city) throws InvalidInputException, GymNotFoundException;

    /**
     * View slots for gym.
     *
     * @param gymId the gym id
     * @param date  the date
     */
    void viewSlotsForGym(String gymId, LocalDate date) throws InvalidInputException, GymNotFoundException;

    /**
     * Gets the available slots.
     *
     * @param gymId the gym id
     * @param date  the date
     * @return the available slots
     */
    List<Slot> getAvailableSlots(String gymId, LocalDate date) throws InvalidInputException;

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
     * @throws InvalidInputException     the invalid input exception
     * @throws BookingCreationException
     */
    boolean bookSlot(String userId, String slotId, String gymId, LocalDate date)
            throws BookingFailedException, SlotNotAvailableException, InvalidInputException, BookingCreationException;

    /**
     * Cancel booking.
     *
     * @param bookingId the booking id
     * @param userId    the user id
     * @return true, if successful
     * @throws BookingFailedException the booking failed exception
     * @throws InvalidInputException  the invalid input exception
     */
    boolean cancelBooking(String bookingId, String userId) throws BookingFailedException, InvalidInputException;

    /**
     * View my bookings.
     *
     * @param userId the user id
     * @throws InvalidInputException the invalid input exception
     */
    void viewMyBookings(String userId) throws InvalidInputException;
}