package com.flipfit.business;

import com.flipfit.bean.Gym;

public class GymOwnerServiceImpl implements GymOwnerInterface {
    @Override
    public void registerGym(Gym gym) {
        System.out.println("Gym '" + gym.getGymName() + "' registered and awaiting admin approval.");
    }

    @Override
    public void viewBookings() {
        System.out.println("Fetching all bookings for your gym centers...");
    }

    @Override
    public void updateSchedule(String gymId) {
        System.out.println("Updating time slots for Gym ID: " + gymId);
    }
}