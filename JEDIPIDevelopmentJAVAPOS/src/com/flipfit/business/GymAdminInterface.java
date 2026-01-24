package com.flipfit.business;
import com.flipfit.bean.Gym;
import java.util.List;

public interface GymAdminInterface {
    List<Gym> viewPendingApprovals();
    void approveGym(String gymId);
    void viewAllUsers();
}