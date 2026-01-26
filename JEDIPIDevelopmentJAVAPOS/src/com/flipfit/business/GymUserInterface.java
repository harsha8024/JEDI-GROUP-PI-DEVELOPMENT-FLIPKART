package com.flipfit.business;
import com.flipfit.bean.User;

public interface GymUserInterface {
    void register(User user); 
    boolean login(String email, String password);
    void logout();
    boolean updatePassword(String email, String oldPassword, String newPassword);
}