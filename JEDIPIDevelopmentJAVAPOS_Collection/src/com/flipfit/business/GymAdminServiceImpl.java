package com.flipfit.business;

import com.flipfit.bean.*;
import com.flipfit.dao.*;
import java.util.*;

public class GymAdminServiceImpl implements GymAdminInterface {

    private GymDAO gymDAO;
    private BookingDAO bookingDAO;
    private CustomerDAO customerDAO;
    private GymOwnerDAO gymOwnerDAO;
    private AdminDAO adminDAO;

    public GymAdminServiceImpl() {
        this.gymDAO = new GymDAO();
        this.bookingDAO = new BookingDAO();
        this.customerDAO = new CustomerDAO();
        this.gymOwnerDAO = new GymOwnerDAO();
        this.adminDAO = new AdminDAO();
    }

    @Override
    public List<Gym> viewPendingApprovals() {
        return gymDAO.getPendingGyms();
    }

    @Override
    public void approveGym(String gymId) {
        if (adminDAO.approveGym(gymId)) {
            System.out.println("✓ Successfully approved gym with ID: " + gymId);
        } else {
            System.out.println("Error: Gym ID " + gymId + " not found or could not be approved.");
        }
    }

    @Override
    public void rejectGym(String gymId) {
        Gym gym = gymDAO.getGymById(gymId);
        if (gym != null) {
            if (adminDAO.rejectGym(gymId)) {
                System.out.println("✓ Successfully rejected and removed gym: " + gym.getGymName());
            } else {
                System.out.println("Error rejecting gym: " + gymId);
            }
        } else {
            System.out.println("Error: Gym ID " + gymId + " not found.");
        }
    }

    @Override
    public void viewAllGyms() {
        List<Gym> allGyms = gymDAO.getAllGyms();

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
        List<Booking> allBookings = bookingDAO.getAllBookings();

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
        Map<String, User> allUsers = new HashMap<>();
        
        // Gather all customers
        Map<String, GymCustomer> customers = customerDAO.getAllCustomers();
        allUsers.putAll(customers);
        
        // Gather all gym owners
        Map<String, GymOwner> owners = gymOwnerDAO.getAllGymOwners();
        allUsers.putAll(owners);
        
        // Gather all admins
        Map<String, GymAdmin> admins = adminDAO.getAllAdmins();
        allUsers.putAll(admins);

        if (allUsers.isEmpty()) {
            System.out.println("No users registered in the system.");
        } else {
            System.out.println("\n========================================");
            System.out.println("        ALL REGISTERED USERS");
            System.out.println("========================================");
            allUsers.values().forEach(user -> {
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
            System.out.println("Total Users: " + allUsers.size());
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
        // Use AdminDAO's getUserCounts method
        Map<String, Integer> userCounts = adminDAO.getUserCounts();
        
        int totalCustomers = userCounts.getOrDefault("customers", 0);
        int totalOwners = userCounts.getOrDefault("gym_owners", 0);
        int totalAdmins = userCounts.getOrDefault("admins", 0);
        int totalUsers = totalCustomers + totalOwners + totalAdmins;
        
        System.out.println("--- USER STATISTICS ---");
        System.out.println("Total Users: " + totalUsers);
        System.out.println("  - Customers: " + totalCustomers);
        System.out.println("  - Gym Owners: " + totalOwners);
        System.out.println("  - Admins: " + totalAdmins);
        
        // Count active users
        Map<String, GymCustomer> customers = customerDAO.getAllCustomers();
        Map<String, GymOwner> owners = gymOwnerDAO.getAllGymOwners();
        Map<String, GymAdmin> admins = adminDAO.getAllAdmins();
        
        long activeCustomers = customers.values().stream().filter(GymCustomer::isActive).count();
        long activeOwners = owners.values().stream().filter(GymOwner::isActive).count();
        long activeAdmins = admins.values().stream().filter(GymAdmin::isActive).count();
        long totalActive = activeCustomers + activeOwners + activeAdmins;
        
        System.out.println("Active Users: " + totalActive);
        System.out.println("Inactive Users: " + (totalUsers - totalActive));
    }

    private void generateGymReport() {
        List<Gym> gyms = gymDAO.getAllGyms();
        int pendingGymsCount = adminDAO.getPendingGymsCount();
        
        System.out.println("--- GYM STATISTICS ---");
        System.out.println("Total Gyms: " + gyms.size());
        System.out.println("Approved Gyms: " + (gyms.size() - pendingGymsCount));
        System.out.println("Pending Gyms: " + pendingGymsCount);
        
        // Inactive gym owners count
        int inactiveOwnersCount = adminDAO.getInactiveGymOwnersCount();
        System.out.println("Inactive Gym Owners: " + inactiveOwnersCount);
    }

    private void generateBookingReport() {
        List<Booking> bookings = bookingDAO.getAllBookings();
        System.out.println("--- BOOKING STATISTICS ---");
        System.out.println("Total Bookings: " + bookings.size());

        long activeBookings = bookings.stream()
                .filter(b -> b.getStatus().equals("CONFIRMED"))
                .count();
        System.out.println("Confirmed Bookings: " + activeBookings);
        System.out.println("Cancelled Bookings: " + (bookings.size() - activeBookings));
    }
}