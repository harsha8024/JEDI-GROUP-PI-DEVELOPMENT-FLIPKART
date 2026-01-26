package com.flipfit.business;

import com.flipfit.bean.Gym;
import com.flipfit.bean.User;
import java.util.List;

public interface GymAdminInterface {
    List<Gym> viewPendingApprovals();
    void approveGym(String gymId);
    void viewAllUsers();
    
    // New methods to match the updated menu
    void rejectGym(String gymId);
    void viewAllGyms();
    void viewAllBookings();
    void generateReports(int reportType);
}