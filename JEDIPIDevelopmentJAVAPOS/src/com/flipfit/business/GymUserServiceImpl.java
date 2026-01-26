package com.flipfit.business;

import com.flipfit.bean.User;
import java.util.HashMap;
import java.util.Map;

public class GymUserServiceImpl implements GymUserInterface {

    private static Map<String, User> userDatabase = new HashMap<>();

    public static Map<String, User> getUserMap() {
        return userDatabase;
    }

    @Override
    public void register(User user) {
        userDatabase.put(user.getEmail(), user);
        System.out.println("Registration successful for: " + user.getName());
    }

    @Override
    public boolean login(String email, String password) {
        if (userDatabase.containsKey(email)) {
            return userDatabase.get(email).getPassword().equals(password);
        }
        return false;
    }

    @Override
    public boolean updatePassword(String email, String oldPassword, String newPassword) {
        if (userDatabase.containsKey(email)) {
            User user = userDatabase.get(email);
            
            if (user.getPassword().equals(oldPassword)) {
                user.setPassword(newPassword);
                userDatabase.put(email, user); 
                System.out.println("Password successfully updated for: " + email);
                return true;
            } else {
                System.out.println("[Error] Old password does not match.");
            }
        } else {
            System.out.println("[Error] User not found.");
        }
        return false;
    }

    @Override
    public void logout() {
        System.out.println("User logged out successfully.");
    }


}