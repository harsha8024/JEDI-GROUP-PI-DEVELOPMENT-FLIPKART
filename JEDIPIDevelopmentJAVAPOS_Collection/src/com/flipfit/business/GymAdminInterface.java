// TODO: Auto-generated Javadoc
package com.flipfit.business;

import com.flipfit.bean.Gym;
import com.flipfit.bean.Slot;
import com.flipfit.exception.ApprovalFailedException;
import com.flipfit.exception.InvalidDateRangeException;
import com.flipfit.exception.InvalidInputException;

import java.util.List;

/**
 * The Interface GymAdminInterface.
 *
 * @author team pi
 * @ClassName "GymAdminInterface"
 */
public interface GymAdminInterface {

    /**
     * View pending approvals.
     *
     * @return the list of pending gyms
     */
    List<Gym> viewPendingApprovals();

    /**
     * Approve gym.
     *
     * @param gymId the gym id
     * @throws ApprovalFailedException the approval failed exception
     */
    void approveGym(String gymId) throws ApprovalFailedException, InvalidInputException;

    /**
     * View all users.
     */
    void viewAllUsers();

    /**
     * Reject gym.
     *
     * @param gymId the gym id
     * @throws ApprovalFailedException the approval failed exception
     */
    void rejectGym(String gymId) throws ApprovalFailedException, InvalidInputException;

    /**
     * View all gyms.
     */
    void viewAllGyms();

    /**
     * View all bookings.
     */
    void viewAllBookings();

    /**
     * Generate reports.
     *
     * @param reportType the report type
     */
    void generateReports(int reportType) throws InvalidInputException;

    /**
     * View approved gyms.
     */
    void viewApprovedGyms();

    /**
     * View pending gyms.
     */
    void viewPendingGyms();

    /**
     * View gyms by location.
     *
     * @param location the location
     */
    void viewGymsByLocation(String location);

    /**
     * View approved gym owners.
     */
    void viewApprovedGymOwners();

    /**
     * View pending gym owners.
     */
    void viewPendingGymOwners();

    /**
     * Approve gym owner.
     *
     * @param ownerId the owner id
     * @throws ApprovalFailedException the approval failed exception
     */
    void approveGymOwner(String ownerId) throws ApprovalFailedException;

    /**
     * Reject gym owner.
     *
     * @param ownerId the owner id
     * @throws ApprovalFailedException the approval failed exception
     */
    void rejectGymOwner(String ownerId) throws ApprovalFailedException;

    /**
     * View payment reports and revenue.
     */
    void viewPaymentReports();

    /**
     * View revenue by date range.
     *
     * @param startDate the start date (yyyy-MM-dd)
     * @param endDate the end date (yyyy-MM-dd)
     * @throws InvalidDateRangeException the invalid date range exception
     */
    void viewRevenueByDateRange(String startDate, String endDate) throws InvalidDateRangeException;

    /**
     * View pending slots.
     *
     * @return the list of pending slots
     */
    // Slot approval methods
    List<Slot> viewPendingSlots();

    /**
     * Approve slot.
     *
     * @param slotId the slot id
     * @throws ApprovalFailedException the approval failed exception
     */
    void approveSlot(String slotId) throws ApprovalFailedException, InvalidInputException;

    /**
     * Reject slot.
     *
     * @param slotId the slot id
     * @throws ApprovalFailedException the approval failed exception
     */
    void rejectSlot(String slotId) throws ApprovalFailedException, InvalidInputException;
}