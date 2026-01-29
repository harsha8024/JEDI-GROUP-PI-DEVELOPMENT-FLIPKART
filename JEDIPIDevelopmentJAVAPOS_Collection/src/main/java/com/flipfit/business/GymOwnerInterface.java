// TODO: Auto-generated Javadoc
package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.Gym;
import com.flipfit.exception.RegistrationFailedException;
import com.flipfit.exception.UserNotFoundException;
import com.flipfit.exception.InvalidInputException;

import java.util.List;

/**
 * The Interface GymOwnerInterface.
 *
 * @author team pi
 * @ClassName "GymOwnerInterface"
 */
public interface GymOwnerInterface {

    /**
     * Register gym.
     *
     * @param gym the gym
     * @throws RegistrationFailedException the registration failed exception
     */
    void registerGym(Gym gym) throws RegistrationFailedException, InvalidInputException;

    /**
     * View bookings.
     *
     * @param ownerId the owner id
     * @throws UserNotFoundException the user not found exception
     */
    List<Booking> viewBookings(String ownerId) throws UserNotFoundException, InvalidInputException;

    /**
     * Update schedule.
     *
     * @param gymId the gym id
     */
    void updateSchedule(String gymId) throws InvalidInputException;

    /**
     * View my gyms.
     *
     * @return the list
     */
    List<Gym> viewMyGyms();

    /**
     * View my gyms.
     *
     * @param ownerId the owner id
     * @return the list
     * @throws UserNotFoundException the user not found exception
     */
    List<Gym> viewMyGyms(String ownerId) throws UserNotFoundException, InvalidInputException;
}