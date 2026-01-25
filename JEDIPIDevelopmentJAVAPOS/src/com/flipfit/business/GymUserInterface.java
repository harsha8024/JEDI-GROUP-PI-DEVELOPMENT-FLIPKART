package com.flipfit.business;

public interface GymUserInterface {
    boolean login(String email, String password);
    void logout();
    boolean updatePassword(String email, String oldPassword, String newPassword);
}