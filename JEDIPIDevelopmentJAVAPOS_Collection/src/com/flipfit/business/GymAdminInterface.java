package com.flipfit.business;

import com.flipfit.bean.Gym;
import com.flipfit.bean.Slot;
import java.util.List;

public interface GymAdminInterface {
    List<Gym> viewPendingApprovals();
    void approveGym(String gymId);
    void viewAllUsers();
    void rejectGym(String gymId);
    void viewAllGyms();
    void viewAllBookings();
    void generateReports(int reportType);
    
    // Slot approval methods
    List<Slot> viewPendingSlots();
    void approveSlot(String slotId);
    void rejectSlot(String slotId);
}