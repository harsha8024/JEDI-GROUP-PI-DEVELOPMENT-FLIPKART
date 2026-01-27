package com.flipfit.business;

import com.flipfit.bean.Gym;
import com.flipfit.exception.RegistrationFailedException;
import com.flipfit.exception.UserNotFoundException;

import java.util.List;

public interface GymOwnerInterface {
    void registerGym(Gym gym) throws RegistrationFailedException;
    void viewBookings();
    void viewBookings(String ownerId) throws UserNotFoundException;
    void updateSchedule(String gymId);
    List<Gym> viewMyGyms();
    List<Gym> viewMyGyms(String ownerId) throws UserNotFoundException;
}