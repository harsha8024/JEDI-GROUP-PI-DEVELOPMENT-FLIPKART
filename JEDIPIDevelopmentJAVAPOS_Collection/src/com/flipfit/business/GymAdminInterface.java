// TODO: Auto-generated Javadoc
package com.flipfit.business;

import com.flipfit.bean.Gym;
import com.flipfit.bean.Slot;
import com.flipfit.exception.ApprovalFailedException;

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
    void approveGym(String gymId) throws ApprovalFailedException;

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
    void rejectGym(String gymId) throws ApprovalFailedException;

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
    void generateReports(int reportType);

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
    void approveSlot(String slotId) throws ApprovalFailedException;

    /**
     * Reject slot.
     *
     * @param slotId the slot id
     * @throws ApprovalFailedException the approval failed exception
     */
    void rejectSlot(String slotId) throws ApprovalFailedException;
}