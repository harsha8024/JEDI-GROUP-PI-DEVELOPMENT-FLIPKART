package com.flipfit.business;

import com.flipfit.bean.Gym;
import com.flipfit.bean.User;
import com.flipfit.bean.Booking;
import com.flipfit.dao.GymDAO;
import com.flipfit.dao.BookingDAO;
import com.flipfit.dao.UserDAO;
import java.util.List;
import java.util.Map;

public class GymAdminServiceImpl implements GymAdminInterface {

    private GymDAO gymDAO;
    private BookingDAO bookingDAO;
    private UserDAO userDAO;

    public GymAdminServiceImpl() {
        this.gymDAO = new GymDAO();
        this.bookingDAO = new BookingDAO();
        this.userDAO = new UserDAO();
    }

    @Override
    public List<Gym> viewPendingApprovals() {
        return gymDAO.getPendingGyms();
    }

    @Override
    public void approveGym(String gymId) {
        if (gymDAO.approveGym(gymId)) {
            System.out.println("✓ Successfully approved gym with ID: " + gymId);
        } else {
            System.out.println("Error: Gym ID " + gymId + " not found or could not be approved.");
        }
    }

    @Override
    public void rejectGym(String gymId) {
        Gym gym = gymDAO.getGymById(gymId);
        if (gym != null) {
            if (gymDAO.deleteGym(gymId)) {
                System.out.println("✓ Successfully rejected and removed gym: " + gym.getGymName());
            } else {
                System.out.println("Error deleting gym: " + gymId);
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
        Map<String, User> users = userDAO.getAllUsers();

        if (users == null || users.isEmpty()) {
            System.out.println("No users registered in the system.");
        } else {
            System.out.println("\n========================================");
            System.out.println("        ALL REGISTERED USERS");
            System.out.println("========================================");
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
        Map<String, User> users = userDAO.getAllUsers();
        System.out.println("--- USER STATISTICS ---");
        System.out.println("Total Users: " + users.size());

        long activeUsers = users.values().stream().filter(User::isActive).count();
        System.out.println("Active Users: " + activeUsers);
        System.out.println("Inactive Users: " + (users.size() - activeUsers));
    }

    private void generateGymReport() {
        List<Gym> gyms = gymDAO.getAllGyms();
        System.out.println("--- GYM STATISTICS ---");
        System.out.println("Total Gyms: " + gyms.size());

        long approvedGyms = gyms.stream().filter(Gym::isApproved).count();
        System.out.println("Approved Gyms: " + approvedGyms);
        System.out.println("Pending Gyms: " + (gyms.size() - approvedGyms));
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