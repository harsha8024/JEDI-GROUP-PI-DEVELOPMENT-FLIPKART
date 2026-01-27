// TODO: Auto-generated Javadoc
package com.flipfit.business;

import com.flipfit.bean.Gym;
import com.flipfit.exception.RegistrationFailedException;
import com.flipfit.exception.UserNotFoundException;

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
    void registerGym(Gym gym) throws RegistrationFailedException;

    /**
     * View bookings.
     */
    void viewBookings();

    /**
     * View bookings.
     *
     * @param ownerId the owner id
     * @throws UserNotFoundException the user not found exception
     */
    void viewBookings(String ownerId) throws UserNotFoundException;

    /**
     * Update schedule.
     *
     * @param gymId the gym id
     */
    void updateSchedule(String gymId);

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
    List<Gym> viewMyGyms(String ownerId) throws UserNotFoundException;
}