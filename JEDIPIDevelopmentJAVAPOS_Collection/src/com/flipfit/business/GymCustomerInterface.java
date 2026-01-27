// TODO: Auto-generated Javadoc
package com.flipfit.business;

import com.flipfit.bean.Slot;
import com.flipfit.exception.BookingFailedException;
import com.flipfit.exception.SlotNotAvailableException;
import java.time.LocalDate;
import java.util.List;

/**
 * The Interface GymCustomerInterface.
 *
 * @author team pi
 * @ClassName "GymCustomerInterface"
 */
public interface GymCustomerInterface {

<<<<<<< Updated upstream
    /**
     * Register.
     *
     * @param user the user
     * @throws RegistrationFailedException the registration failed exception
     */
    void register(User user) throws RegistrationFailedException;

    /**
     * View centers.
     *
     * @param city the city
     */
    void viewCenters(String city);

    /**
     * View slots for gym.
     *
     * @param gymId the gym id
     * @param date  the date
     */
    void viewSlotsForGym(String gymId, LocalDate date);

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
    boolean bookSlot(String userId, String slotId, String gymId, LocalDate date)
            throws BookingFailedException, SlotNotAvailableException;

    /**
     * Cancel booking.
     *
     * @param bookingId the booking id
     * @param userId    the user id
     * @return true, if successful
     * @throws BookingFailedException the booking failed exception
     */
    boolean cancelBooking(String bookingId, String userId) throws BookingFailedException;

    /**
     * View my bookings.
     *
     * @param userId the user id
     */
    void viewMyBookings(String userId);

    /**
     * Gets the available slots.
     *
     * @param gymId the gym id
     * @param date  the date
     * @return the available slots
     */
=======
    // REMOVED: register(User user) -> Moved to RegistrationInterface/GymUserInterface

    // BROWSING (Stays)
    void viewCenters(String city);
    
    void viewSlotsForGym(String gymId, LocalDate date);
    
>>>>>>> Stashed changes
    List<Slot> getAvailableSlots(String gymId, LocalDate date);

    // BOOKING ACTIONS (Stays - acts as a wrapper for Booking/Payment services)
    boolean bookSlot(String userId, String slotId, String gymId, LocalDate date) 
        throws BookingFailedException, SlotNotAvailableException;
    
    boolean cancelBooking(String bookingId, String userId) 
        throws BookingFailedException;
    
    void viewMyBookings(String userId);
}