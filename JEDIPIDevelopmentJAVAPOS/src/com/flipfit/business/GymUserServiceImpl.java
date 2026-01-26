package com.flipfit.business;

import com.flipfit.bean.User;
import com.flipfit.bean.Role;
import com.flipfit.database.LocalFileDatabase;
import java.util.HashMap;
import java.util.Map;

public class GymUserServiceImpl implements GymUserInterface {
    // Centralized User Collection - loaded from file database
    private static Map<String, User> userDatabase = new HashMap<>();

    static {
        // Load existing users from file on startup
        userDatabase = LocalFileDatabase.loadUsers();
    }

    // Static getter for Admin service access
    public static Map<String, User> getUserMap() {
        return userDatabase;
    }

    @Override
    public void register(User user) {
        // Generate unique user ID
        String userId = LocalFileDatabase.generateUserId();
        user.setUserID(userId);
        
        // Set default role if not set
        if (user.getRole() == null) {
            Role role = new Role();
            role.setRoleName("CUSTOMER");
            role.setDescription("Regular gym customer");
            user.setRole(role);
        }
        
        // Set user as active
        user.setActive(true);
        
        // Add to in-memory map
        userDatabase.put(user.getEmail(), user);
        
        // Save to file
        LocalFileDatabase.saveUser(user);
        
        System.out.println("Registration successful for: " + user.getName() + " (ID: " + userId + ")");
    }

    @Override
    public boolean login(String email, String password) {
        if (userDatabase.containsKey(email)) {
            User user = userDatabase.get(email);
            if (user.getPassword().equals(password)) {
                user.setActive(true);
                LocalFileDatabase.updateUser(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        if (userDatabase.containsKey(email)) {
            User user = userDatabase.get(email);
            user.setPassword(newPassword);
            userDatabase.put(email, user);
            LocalFileDatabase.updateUser(user);
            System.out.println("Password updated successfully!");
        } else {
            System.out.println("User not found!");
        }
    }

    @Override
    public void logout() {
        System.out.println("User logged out successfully.");
    }

}