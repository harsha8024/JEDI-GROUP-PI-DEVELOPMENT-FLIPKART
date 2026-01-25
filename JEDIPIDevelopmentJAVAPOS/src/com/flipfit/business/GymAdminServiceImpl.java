package com.flipfit.business;

import com.flipfit.bean.Gym;
import com.flipfit.bean.User;
import java.util.List;
import java.util.Map;

public class GymAdminServiceImpl implements GymAdminInterface {

    @Override
    public List<Gym> viewPendingApprovals() {
        // Access shared list and filter for isApproved == false
        return GymOwnerServiceImpl.getGymList().stream()
                .filter(gym -> !gym.isApproved())
                .toList();
    }

    @Override
    public void approveGym(String gymId) {
        GymOwnerServiceImpl.getGymList().stream()
                .filter(gym -> gym.getGymId().equals(gymId))
                .findFirst()
                .ifPresentOrElse(
                    gym -> {
                        gym.setApproved(true);
                        System.out.println("Gym " + gymId + " approved successfully!");
                    },
                    () -> System.out.println("Gym ID not found.")
                );
    }

    @Override
    public void rejectGym(String gymId) {
        // Remove the gym from the shared list if rejected
        boolean removed = GymOwnerServiceImpl.getGymList()
                .removeIf(gym -> gym.getGymId().equals(gymId));
        
        if (removed) {
            System.out.println("Gym " + gymId + " has been rejected and removed.");
        } else {
            System.out.println("Gym ID not found.");
        }
    }

    @Override
    public void viewAllGyms() {
        List<Gym> gyms = GymOwnerServiceImpl.getGymList();
        if (gyms.isEmpty()) {
            System.out.println("No gyms registered in the system.");
        } else {
            gyms.forEach(g -> System.out.println("ID: " + g.getGymId() + " | Name: " + g.getGymName() + " | Status: " + (g.isApproved() ? "Approved" : "Pending")));
        }
    }

    @Override
    public void viewAllUsers() {
        Map<String, User> users = GymUserServiceImpl.getUserMap();
        if (users.isEmpty()) {
            System.out.println("No users registered.");
        } else {
            users.values().forEach(u -> System.out.println("Name: " + u.getName() + " | Email: " + u.getEmail() + " | City: " + u.getCity()));
        }
    }

    @Override
    public void viewAllBookings() {
        // This would typically access a shared booking list from Customer Service
        System.out.println("Fetching all system-wide bookings...");
    }

    @Override
    public void generateReports(int reportType) {
        switch (reportType) {
            case 1 -> System.out.println("Report: Total Revenue generated is $5000.");
            case 2 -> System.out.println("Report: 50 New Users registered this month.");
            case 3 -> System.out.println("Report: Gym 'PowerHouse' has 90% utilization.");
            default -> System.out.println("Invalid report type.");
        }
    }
}