// TODO: Auto-generated Javadoc
package com.flipfit.business;

import com.flipfit.bean.*;
import com.flipfit.dao.*;
import com.flipfit.exception.ApprovalFailedException;
import com.flipfit.exception.InvalidDateRangeException;
import com.flipfit.exception.InvalidInputException;

import java.util.*;

/**
 * The Class GymAdminServiceImpl.
 *
 * @author team pi
 * @ClassName "GymAdminServiceImpl"
 */
public class GymAdminServiceImpl implements GymAdminInterface {

    /** The gym DAO. */
    private GymDAO gymDAO;

    /** The booking DAO. */
    private BookingDAO bookingDAO;

    /** The customer DAO. */
    private CustomerDAO customerDAO;

    /** The gym owner DAO. */
    private GymOwnerDAO gymOwnerDAO;

    /** The admin DAO. */
    private AdminDAO adminDAO;

    /** The slot DAO. */
    private SlotDAO slotDAO;

    /** The payment DAO. */
    private PaymentDAO paymentDAO;

    /**
     * Instantiates a new gym admin service impl.
     */
    public GymAdminServiceImpl() {
        this.gymDAO = new GymDAO();
        this.bookingDAO = new BookingDAO();
        this.customerDAO = new CustomerDAO();
        this.gymOwnerDAO = new GymOwnerDAO();
        this.adminDAO = new AdminDAO();
        this.slotDAO = new SlotDAO();
        this.paymentDAO = new PaymentDAO();
    }

    /**
     * View pending approvals.
     *
     * @return the list
     */
    @Override
    public List<Gym> viewPendingApprovals() {
        return gymDAO.getPendingGyms();
    }

    /**
     * Approve gym.
     *
     * @param gymId the gym id
     * @throws ApprovalFailedException the approval failed exception
     */
    @Override
    public void approveGym(String gymId) throws ApprovalFailedException, InvalidInputException {
        if (gymId == null || gymId.isBlank()) throw new InvalidInputException("Invalid Gym ID.");
        if (!adminDAO.approveGym(gymId)) { //
            throw new ApprovalFailedException("Gym ID " + gymId + " not found or could not be approved."); //
        }
        System.out.println("✓ Successfully approved gym with ID: " + gymId);
    }

    /**
     * Reject gym.
     *
     * @param gymId the gym id
     * @throws ApprovalFailedException the approval failed exception
     */
    @Override
    public void rejectGym(String gymId) throws ApprovalFailedException, InvalidInputException {
        if (gymId == null || gymId.isBlank()) throw new InvalidInputException("Invalid Gym ID.");
        Gym gym = gymDAO.getGymById(gymId); //
        if (gym == null) {
            throw new ApprovalFailedException("Error: Gym ID " + gymId + " not found."); //
        }
        if (!adminDAO.rejectGym(gymId)) { //
            throw new ApprovalFailedException("Database error: Could not remove gym " + gymId); //
        }
        System.out.println("✓ Successfully rejected and removed gym: " + gym.getGymName());
    }

    /**
     * View all gyms.
     */
    @Override
    public void viewAllGyms() {
        List<Gym> allGyms = gymDAO.getAllGyms();

        if (allGyms.isEmpty()) {
            System.out.println("No gyms registered in the system.");
        } else {
            System.out.println("\n========================================");
            System.out.println("         ALL REGISTERED GYMS");
            System.out.println("========================================");
            allGyms.forEach(gym -> {
                String status = gym.isApproved() ? "APPROVED" : "PENDING";
                System.out.println("ID: " + gym.getGymId() +
                        " | Name: " + gym.getGymName() +
                        " | Location: " + gym.getLocation() +
                        " | Owner: " + gym.getGymOwnerId() +
                        " | Status: " + status);
            });
            System.out.println("========================================");
            System.out.println("Total Gyms: " + allGyms.size());
        }
    }

    /**
     * View all bookings.
     */
    @Override
    public void viewAllBookings() {
        List<Booking> allBookings = bookingDAO.getAllBookings();

        if (allBookings.isEmpty()) {
            System.out.println("No bookings found in the system.");
        } else {
            System.out.println("\n========================================");
            System.out.println("          ALL BOOKINGS");
            System.out.println("========================================");
            allBookings.forEach(booking ->
                    System.out.println("Booking ID: " + booking.getBookingId() +
                            " | User: " + booking.getUserId() +
                            " | Gym: " + booking.getGymId() +
                            " | Slot: " + booking.getSlotId() +
                            " | Date: " + booking.getBookingDate() +
                            " | Status: " + booking.getStatus() +
                            " | Created: " + booking.getCreatedAt())
            );
            System.out.println("========================================");
            System.out.println("Total Bookings: " + allBookings.size());
        }
    }

    /**
     * View all users.
     */
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

    /**
     * Generate reports.
     *
     * @param reportType the report type
     */
    @Override
    public void generateReports(int reportType) throws InvalidInputException {
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
                throw new InvalidInputException("Invalid report type.");
        }
        System.out.println("========================================");
    }

    /**
     * Generate user report.
     */
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

    /**
     * Generate gym report.
     */
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

        // Pending slots count
        int pendingSlotsCount = adminDAO.getPendingSlotsCount();
        System.out.println("Pending Slots: " + pendingSlotsCount);
    }

    /**
     * Generate booking report.
     */
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

    // ==================== SLOT APPROVAL METHODS ====================

    /**
     * View pending slots.
     *
     * @return the list
     */
    @Override
    public List<Slot> viewPendingSlots() {
        return slotDAO.getPendingSlots();
    }

    /**
     * Approve slot.
     *
     * @param slotId the slot id
     * @throws ApprovalFailedException the approval failed exception
     */
    @Override
    public void approveSlot(String slotId) throws ApprovalFailedException, InvalidInputException {
        if (slotId == null || slotId.isBlank()) throw new InvalidInputException("Invalid slot ID.");
        if (!adminDAO.approveSlot(slotId)) { //
            throw new ApprovalFailedException("Slot ID " + slotId + " could not be approved."); //
        }
        System.out.println("✓ Successfully approved slot with ID: " + slotId);
    }

    /**
     * Reject slot.
     *
     * @param slotId the slot id
     * @throws ApprovalFailedException the approval failed exception
     */
    @Override
    public void rejectSlot(String slotId) throws ApprovalFailedException, InvalidInputException {
        if (slotId == null || slotId.isBlank()) throw new InvalidInputException("Invalid slot ID.");
        if (!adminDAO.rejectSlot(slotId)) { //
            throw new ApprovalFailedException("Slot ID " + slotId + " not found or could not be rejected."); //
        }
        System.out.println("✓ Successfully rejected slot: " + slotId);
    }

    /**
     * View payment reports and revenue.
     */
    @Override
    public void viewPaymentReports() {
        System.out.println("\n========================================");
        System.out.println("       PAYMENT & REVENUE REPORTS");
        System.out.println("========================================");

        // Get total revenue
        java.math.BigDecimal totalRevenue = paymentDAO.getTotalRevenue();
        System.out.println("\nTotal Revenue (All Time): ₹" + totalRevenue);

        // Get all payments
        List<Payment> allPayments = paymentDAO.getAllPayments();
        System.out.println("\nTotal Transactions: " + allPayments.size());

        if (!allPayments.isEmpty()) {
            System.out.println("\n--- Recent Payments (Last 10) ---");
            int count = 0;
            for (Payment payment : allPayments) {
                if (count >= 10) break;
                System.out.println(String.format("Payment ID: %s | Booking: %s | Customer: %s | Amount: ₹%.2f | Method: %s | Status: %s | Date: %s",
                    payment.getPaymentId(),
                    payment.getBookingId(),
                    payment.getUserId(),
                    payment.getAmount(),
                    payment.getPaymentMethod(),
                    payment.getPaymentStatus(),
                    payment.getPaymentDate()));
                count++;
            }
        }

        System.out.println("\n========================================");
    }

    /**
     * View revenue by date range.
     *
     * @param startDate the start date (yyyy-MM-dd)
     * @param endDate the end date (yyyy-MM-dd)
     */
    @Override
    public void viewRevenueByDateRange(String startDate, String endDate) throws InvalidDateRangeException {
        try {
            java.sql.Timestamp start = java.sql.Timestamp.valueOf(startDate + " 00:00:00");
            java.sql.Timestamp end = java.sql.Timestamp.valueOf(endDate + " 23:59:59");

            System.out.println("\n========================================");
            System.out.println("   REVENUE REPORT: " + startDate + " to " + endDate);
            System.out.println("========================================");

            java.math.BigDecimal revenue = paymentDAO.getRevenueByDateRange(start, end);
            List<Payment> payments = paymentDAO.getPaymentsByDateRange(start, end);

            System.out.println("\nTotal Revenue: ₹" + revenue);
            System.out.println("Total Transactions: " + payments.size());

            if (!payments.isEmpty()) {
                System.out.println("\n--- Payment Details ---");
                for (Payment payment : payments) {
                    System.out.println(String.format("Payment ID: %s | Amount: ₹%.2f | Customer: %s | Date: %s | Status: %s",
                        payment.getPaymentId(),
                        payment.getAmount(),
                        payment.getUserId(),
                        payment.getPaymentDate(),
                        payment.getPaymentStatus()));
                }
            }

            System.out.println("\n========================================");
        } catch (Exception e) {
            throw new InvalidDateRangeException("Invalid date format. Please use yyyy-MM-dd format.", e);
        }
    }
}