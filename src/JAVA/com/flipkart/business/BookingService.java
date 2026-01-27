package com.flipkart.business;

public interface BookingService {
    boolean bookWorkout(String userId, String slotId, String date);
    void cancelWorkout(String bookingId);
}