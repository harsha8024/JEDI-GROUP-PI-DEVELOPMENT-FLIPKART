package com.flipfit.business;

public interface GymUserInterface {
    boolean login(String email, String password);
    void logout();
    void updateProfile();
}