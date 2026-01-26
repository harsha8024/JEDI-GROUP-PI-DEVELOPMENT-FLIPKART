package com.flipfit.business;

import com.flipfit.bean.Gym;
import com.flipfit.bean.User;
import com.flipfit.bean.Booking;
import com.flipfit.database.LocalFileDatabase;
import java.util.List;
import java.util.Map;
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
                LocalFileDatabase.updateGym(gym);
                System.out.println("✓ Successfully approved " + gym.getGymName());
                return;
            }
        }
        System.out.println("Error: Gym ID " + gymId + " not found.");
    }

    @Override
    public void rejectGym(String gymId) {
        List<Gym> allGyms = GymOwnerServiceImpl.getGymList();
        Gym toRemove = null;
        for (Gym gym : allGyms) {
            if (gym.getGymId().equals(gymId)) {
                toRemove = gym;
                break;
            }
        }
        
        if (toRemove != null) {
            allGyms.remove(toRemove);
            // Reload gyms and save without the rejected gym
            List<Gym> updatedGyms = LocalFileDatabase.loadGyms().stream()
                .filter(g -> !g.getGymId().equals(gymId))
                .collect(Collectors.toList());
            
            // Rewrite the gym file
            try {
                java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter("database/gyms.txt"));
                for (Gym gym : updatedGyms) {
                    writer.println(gym.getGymId() + "|" + gym.getGymName() + "|" + 
                                   gym.getLocation() + "|" + gym.getGymOwnerId() + "|" + gym.isApproved());
                }
                writer.close();
                System.out.println("✓ Successfully rejected gym: " + toRemove.getGymName());
            } catch (Exception e) {
                System.out.println("Error rejecting gym: " + e.getMessage());
            }
        } else {
            System.out.println("Error: Gym ID " + gymId + " not found.");
        }
    }

    @Override
    public void viewAllGyms() {
        List<Gym> allGyms = GymOwnerServiceImpl.getGymList();
        
        if (allGyms.isEmpty()) {
            System.out.println("No gyms registered in the system.");
        } else {
            System.out.println("\n========================================");
            System.out.println("         ALL REGISTERED GYMS");
            System.out.println("========================================");
            for (Gym gym : allGyms) {
                String status = gym.isApproved() ? "✓ APPROVED" : "⏳ PENDING";
                System.out.println("ID: " + gym.getGymId() + 
                                   " | Name: " + gym.getGymName() + 
                                   " | Location: " + gym.getLocation() +
                                   " | Owner: " + gym.getGymOwnerId() +
                                   " | Status: " + status);
            }
            System.out.println("========================================");
            System.out.println("Total Gyms: " + allGyms.size());
        }
    }

    @Override
    public void viewAllBookings() {
        List<Booking> allBookings = LocalFileDatabase.loadBookings();
        
        if (allBookings.isEmpty()) {
            System.out.println("No bookings found in the system.");
        } else {
            System.out.println("\n========================================");
            System.out.println("          ALL BOOKINGS");
            System.out.println("========================================");
            for (Booking booking : allBookings) {
                System.out.println("Booking ID: " + booking.getBookingId() + 
                                   " | User: " + booking.getUserId() + 
                                   " | Gym: " + booking.getGymId() + 
                                   " | Slot: " + booking.getSlotId() + 
                                   " | Date: " + booking.getBookingDate() + 
                                   " | Status: " + booking.getStatus() +
                                   " | Created: " + booking.getCreatedAt());
            }
            System.out.println("========================================");
            System.out.println("Total Bookings: " + allBookings.size());
        }
    }

    @Override
    public void viewAllUsers() {
        // Calling the static getter we implemented in GymUserServiceImpl
        Map<String, User> users = GymUserServiceImpl.getUserMap();
        
        if (users == null || users.isEmpty()) {
            System.out.println("No users registered in the system.");
        } else {
            System.out.println("\n========================================");
            System.out.println("        ALL REGISTERED USERS");
            System.out.println("========================================");
            // Using Collection API values() to get all User objects from the Map
            users.values().forEach(user -> {
                String roleStr = user.getRole() != null ? user.getRole().getRoleName() : "N/A";
                String activeStatus = user.isActive() ? "✓ Active" : "○ Inactive";
                System.out.println("ID: " + user.getUserID() + 
                                   " | Name: " + user.getName() + 
                                   " | Email: " + user.getEmail() + 
                                   " | City: " + user.getCity() +
                                   " | Role: " + roleStr +
                                   " | Status: " + activeStatus);
            });
            System.out.println("========================================");
            System.out.println("Total Users: " + users.size());
        }
    }

    @Override
    public void generateReports(int reportType) {
        System.out.println("\n========================================");
        System.out.println("          SYSTEM REPORT");
        System.out.println("========================================");
        
        switch (reportType) {
            case 1: // User Statistics
                generateUserReport();
                break;
            case 2: // Gym Statistics
                generateGymReport();
                break;
            case 3: // Booking Statistics
                generateBookingReport();
                break;
            case 4: // Complete System Report
                generateUserReport();
                System.out.println();
                generateGymReport();
                System.out.println();
                generateBookingReport();
                break;
            default:
                System.out.println("Invalid report type.");
        }
        System.out.println("========================================");
    }

    private void generateUserReport() {
        Map<String, User> users = GymUserServiceImpl.getUserMap();
        System.out.println("--- USER STATISTICS ---");
        System.out.println("Total Users: " + users.size());
        
        long activeUsers = users.values().stream().filter(User::isActive).count();
        System.out.println("Active Users: " + activeUsers);
        System.out.println("Inactive Users: " + (users.size() - activeUsers));
    }

    private void generateGymReport() {
        List<Gym> gyms = GymOwnerServiceImpl.getGymList();
        System.out.println("--- GYM STATISTICS ---");
        System.out.println("Total Gyms: " + gyms.size());
        
        long approvedGyms = gyms.stream().filter(Gym::isApproved).count();
        System.out.println("Approved Gyms: " + approvedGyms);
        System.out.println("Pending Gyms: " + (gyms.size() - approvedGyms));
        
        // Group by city
        Map<String, Long> gymsByCity = gyms.stream()
            .collect(Collectors.groupingBy(Gym::getLocation, Collectors.counting()));
        System.out.println("Gyms by City: " + gymsByCity);
    }

    private void generateBookingReport() {
        List<Booking> bookings = LocalFileDatabase.loadBookings();
        System.out.println("--- BOOKING STATISTICS ---");
        System.out.println("Total Bookings: " + bookings.size());
        
        long activeBookings = bookings.stream()
            .filter(b -> b.getStatus().equals("ACTIVE"))
            .count();
        System.out.println("Active Bookings: " + activeBookings);
        System.out.println("Cancelled Bookings: " + (bookings.size() - activeBookings));
    }
}