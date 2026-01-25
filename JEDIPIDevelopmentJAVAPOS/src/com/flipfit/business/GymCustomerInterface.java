package com.flipfit.business;

public interface GymCustomerInterface {
    void register();
    void viewCenters(String city);
    boolean bookSlot(String slotId);
    boolean cancelBooking(String bookingId);
    void viewAvailableSlots(String gymId, String date);
    void viewMyBookings();
    void viewPlanByDay(String date);
    boolean updateProfile(String email, String name, String phone, String city);
    void logout();
}