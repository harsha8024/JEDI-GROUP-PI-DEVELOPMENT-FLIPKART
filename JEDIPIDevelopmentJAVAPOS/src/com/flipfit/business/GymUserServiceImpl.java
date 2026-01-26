package com.flipfit.business;

import com.flipfit.bean.GymAdmin;
import com.flipfit.bean.User;
import java.util.HashMap;
import java.util.Map;

public class GymUserServiceImpl implements GymUserInterface {

    private static Map<String, User> userDatabase = new HashMap<>();
    
    static {
        System.out.println("Booting up system... Creating default Admin account.");
        
        // 1. Create the single Admin object
        GymAdmin defaultAdmin = new GymAdmin();
        defaultAdmin.setUserID("ADMIN-001");
        defaultAdmin.setName("FlipFit Admin");
        defaultAdmin.setEmail("admin@flipfit.com"); // The fixed email
        defaultAdmin.setPassword("admin123");       // The fixed password
        defaultAdmin.setCity("Headquarters");
        defaultAdmin.setPhoneNumber("9999999999");
        defaultAdmin.setRole(new Role("RO3", "Admin", "System Administrator"));
        defaultAdmin.setActive(true);

        // 2. Add it to the map immediately
        userDatabase.put(defaultAdmin.getEmail(), defaultAdmin);
    }

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