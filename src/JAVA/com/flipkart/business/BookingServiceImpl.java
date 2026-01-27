package com.flipkart.business;

public class BookingServiceImpl implements BookingService {
    @Override
    public boolean bookWorkout(String userId, String slotId, String date) {
        // 1. Conflict Check (Req #3): Delete existing booking for user at this time
        // bookingDAO.deleteExistingBooking(userId, slotId, date);

        // 2. Capacity Check (Req #5): Check DB for available seats
        int availableSeats = 10; // This should come from your DB query
        if (availableSeats > 0) {
            // Confirm booking
            return true;
        } else {
            // Add to Waitlist (Bonus Requirement)
            System.out.println("No seats available. Added to waitlist.");
            return false;
        }
    }

    @Override
    public void cancelWorkout(String bookingId) {
        // Logic: Mark booking as cancelled in DB
        // If someone is on the waitlist, trigger NotificationService
    }
}