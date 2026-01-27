package com.flipfit.business;

import com.flipfit.bean.Gym;
import com.flipfit.bean.Slot;
import com.flipfit.exception.ApprovalFailedException;

import java.util.List;

public interface GymAdminInterface {
    List<Gym> viewPendingApprovals();
    void approveGym(String gymId) throws ApprovalFailedException;
    void viewAllUsers();
    void rejectGym(String gymId) throws ApprovalFailedException;
    void viewAllGyms();
    void viewAllBookings();
    void generateReports(int reportType);
    
    // Slot approval methods
    List<Slot> viewPendingSlots();
    void approveSlot(String slotId) throws ApprovalFailedException;
    void rejectSlot(String slotId) throws ApprovalFailedException;
}