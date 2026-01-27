package com.flipfit.business;

import com.flipfit.bean.Gym;
import java.util.List;

public interface GymOwnerInterface {
    void registerGym(Gym gym);
    void viewBookings();
    void viewBookings(String ownerId);
    void updateSchedule(String gymId);
    List<Gym> viewMyGyms();
    List<Gym> viewMyGyms(String ownerId);
}