package com.flipfit.business;

import com.flipfit.bean.Gym;
import java.util.*;

public interface GymOwnerInterface {
    void registerGym(Gym gym);
    void viewBookings();
    List<Gym> viewMyGyms(); 
    void addSlot(String gymId, String startTime, int seats);
    void updateProfile(String email, String newName, String newCity);
    void updateGymDetails(String gymId);
}