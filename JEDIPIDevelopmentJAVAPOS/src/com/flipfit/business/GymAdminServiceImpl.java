package com.flipfit.business;

import com.flipfit.bean.Gym;
import com.flipfit.bean.User; // Added import
import java.util.List;
import java.util.Map; // Added import
import java.util.stream.Collectors;

public class GymAdminServiceImpl implements GymAdminInterface {

    @Override
    public List<Gym> viewPendingApprovals() {
        // Accessing the shared list via the static getter
        return GymOwnerServiceImpl.getGymList().stream()
                .filter(gym -> !gym.isApproved())
                .collect(Collectors.toList());
    }

    @Override
    public void approveGym(String gymId) {
        List<Gym> allGyms = GymOwnerServiceImpl.getGymList();
        for (Gym gym : allGyms) {
            if (gym.getGymId().equals(gymId)) {
                gym.setApproved(true);
                System.out.println("Successfully approved " + gym.getGymName());
                return;
            }
        }
        System.out.println("Gym ID " + gymId + " not found.");
    }

    @Override
    public void viewAllUsers() {
        // Calling the static getter we implemented in GymUserServiceImpl
        Map<String, User> users = GymUserServiceImpl.getUserMap();
        
        if (users == null || users.isEmpty()) {
            System.out.println("No users registered in the system.");
        } else {
            System.out.println("\n--- All Registered Users ---");
            // Using Collection API values() to get all User objects from the Map
            users.values().forEach(user -> {
                System.out.println("ID: " + user.getUserID() + 
                                   " | Name: " + user.getName() + 
                                   " | Email: " + user.getEmail() + 
                                   " | Role: " + user.getRole());
            });
        }
    }
}