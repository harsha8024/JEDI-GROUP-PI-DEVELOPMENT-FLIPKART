package com.flipfit.business;

import com.flipfit.bean.User;

public interface GymCustomerInterface {
    void register(User user); 
    void viewCenters(String city);
    boolean bookSlot(String slotId);
    boolean cancelBooking(String bookingId);
    void viewAvailableSlots(String gymId, String date);
    void viewMyBookings();
    void viewPlanByDay(String date);
    boolean updateProfile(String email, String name, String phone, String city);
    void logout();
	void register();
}