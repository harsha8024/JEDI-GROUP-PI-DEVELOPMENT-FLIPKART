package com.flipfit.business;

import com.flipfit.bean.Booking;
import java.time.LocalDate; // Use modern Java Time
import java.util.List;

public interface BookingInterface {
    // Changed java.util.Date to LocalDate
    boolean checkBookingOverlap(String userId, LocalDate date, String slotId);
    
    String addBooking(String userId, String slotId, String gymId);
    boolean cancelBooking(String bookingId);
    Booking getBookingById(String bookingId);
    List<Booking> getBookingsByUserId(String userId);
    List<Booking> getBookingsByGymId(String gymId);
}