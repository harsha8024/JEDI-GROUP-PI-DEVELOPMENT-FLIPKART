package com.flipfit.business;

import com.flipfit.bean.Gym;

public interface GymOwnerInterface {
    void registerGym(Gym gym);
    void viewBookings();
    void updateSchedule(String gymId);
}