package com.flipfit.business;

import com.flipfit.bean.User;
import java.util.HashMap;
import java.util.Map;

public class GymUserServiceImpl implements GymUserInterface {
    // Centralized User Collection
    private static Map<String, User> userDatabase = new HashMap<>();

    // Static getter for Admin service access
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
    public void updatePassword(String email, String newPassword) {
        if (userDatabase.containsKey(email)) {
            userDatabase.get(email).setPassword(newPassword);
        }
    }

    @Override
    public void logout() {
        System.out.println("User logged out successfully.");
    }

}