package com.flipfit.business;

import com.flipfit.bean.User;
import java.util.HashMap;
import java.util.Map;

public class GymUserServiceImpl implements GymUserInterface {
    // Map to store User objects with Email as the Key
    private static Map<String, User> userDatabase = new HashMap<>();

    @Override
    public boolean login(String email, String password) {
        if (userDatabase.containsKey(email)) {
            User user = userDatabase.get(email);
            return user.getPassword().equals(password);
        }
        return false; 
    }

    // New method to handle registration in the Collection
    public void register(User user) {
        userDatabase.put(user.getEmail(), user);
        System.out.println("User " + user.getName() + " added to Collection.");
    }

    @Override
    public void logout() {
        System.out.println("User has been logged out successfully.");
    }
    
    @Override
    public boolean updatePassword(String email, String oldPassword, String newPassword) {
        // 1. Check if the user exists in our static Map
        if (userDatabase.containsKey(email)) {
            User user = userDatabase.get(email);
            
            // 2. Verify the old password matches what's in our Collection
            if (user.getPassword().equals(oldPassword)) {
                // 3. Update the password in the Bean object
                user.setPassword(newPassword);
                
                // 4. Put it back in the Map (this replaces the old entry)
                userDatabase.put(email, user);
                return true;
            } else {
                System.out.println("Error: Current password does not match.");
            }
        } else {
            System.out.println("Error: User with email " + email + " not found.");
        }
        return false;
    }

}