package com.flipfit.business;

import com.flipfit.bean.Gym;
import java.util.ArrayList;
import java.util.List;

public class GymOwnerServiceImpl implements GymOwnerInterface {
    // Shared collection for all gym data
    private static List<Gym> gymList = new ArrayList<>();
    static {
        gymList.add(new Gym("G101", "PowerHouse Gym", "Bengaluru", true));
        gymList.add(new Gym("G102", "FitLife Center", "Mumbai", false));
    }

    // CRITICAL: This method allows Admin and Customer to access the collection
    public static List<Gym> getGymList() {
        return gymList;
    }

    @Override
    public void registerGym(Gym gym) {
        gymList.add(gym);
        System.out.println("Gym: " + gym.getGymName() + " registered and pending approval.");
    }
    
    @Override
    public List<Gym> viewMyGyms() {
        return gymList; 
    }

    @Override
    public void viewBookings() {
        System.out.println("Displaying bookings for your gyms...");
    }

    @Override
    public void updateSchedule(String gymId) {
        System.out.println("Updating schedule for Gym ID: " + gymId);
    }
}