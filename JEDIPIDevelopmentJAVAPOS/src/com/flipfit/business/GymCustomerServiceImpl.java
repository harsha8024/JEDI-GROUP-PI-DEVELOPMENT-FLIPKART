package com.flipfit.business;

public class GymCustomerServiceImpl implements GymCustomerInterface {
    @Override
    public void register() {
        System.out.println("Customer registration logic processing...");
    }

    @Override
    public void viewCenters(String city) {
        System.out.println("Fetching gym centers in city: " + city);
    }

    @Override
    public boolean bookSlot(String slotId) {
        System.out.println("Booking slot with ID: " + slotId);
        return true; 
    }

    @Override
    public boolean cancelBooking(String bookingId) {
        System.out.println("Cancelling booking with ID: " + bookingId);
        return true;
    }
}