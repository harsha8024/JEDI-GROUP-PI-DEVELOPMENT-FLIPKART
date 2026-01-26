package com.flipfit.business;
import com.flipfit.bean.User;

public interface GymUserInterface {
    void register(User user); 
    boolean login(String email, String password);
    void updatePassword(String email, String newPassword);
    void logout();
}