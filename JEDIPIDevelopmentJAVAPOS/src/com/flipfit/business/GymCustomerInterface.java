package com.flipfit.business;

public interface GymCustomerInterface {
    void register();
    void viewCenters(String city);
    boolean bookSlot(String slotId);
    boolean cancelBooking(String bookingId);
}