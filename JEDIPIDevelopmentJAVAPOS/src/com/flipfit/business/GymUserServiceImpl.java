package com.flipfit.business;

public class GymUserServiceImpl implements GymUserInterface {
    @Override
    public boolean login(String email, String password) {
        System.out.println("User with email: " + email + " is logging in...");
        return true; 
    }

    @Override
    public void logout() {
        System.out.println("User has been logged out successfully.");
    }

    @Override
    public void updateProfile() {
        System.out.println("User profile has been updated.");
    }
}