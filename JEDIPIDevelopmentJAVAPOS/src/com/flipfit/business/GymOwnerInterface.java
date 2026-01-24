package com.flipfit.business;

import com.flipfit.bean.Gym;
import java.util.List;

public interface GymOwnerInterface {
    void registerGym(Gym gym);
    void viewBookings();
    void updateSchedule(String gymId);
    List<Gym> viewMyGyms(); 
}