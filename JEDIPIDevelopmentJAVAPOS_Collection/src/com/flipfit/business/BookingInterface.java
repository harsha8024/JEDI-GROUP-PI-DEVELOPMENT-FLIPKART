package com.flipfit.business;

import com.flipfit.bean.Booking;
import java.time.LocalDate; // Use modern Java Time
import java.util.List;
import com.flipfit.exception.InvalidInputException;
import com.flipfit.exception.BookingCreationException;
import com.flipfit.exception.BookingCancellationException;
import com.flipfit.exception.BookingNotFoundException;

public interface BookingInterface {
    // Changed java.util.Date to LocalDate
    boolean checkBookingOverlap(String userId, LocalDate date, String slotId) throws InvalidInputException;
    
    String addBooking(String userId, String slotId, String gymId) throws InvalidInputException, BookingCreationException;
    boolean cancelBooking(String bookingId) throws InvalidInputException, BookingCancellationException;
    Booking getBookingById(String bookingId) throws InvalidInputException, BookingNotFoundException;
    List<Booking> getBookingsByUserId(String userId) throws InvalidInputException;
    List<Booking> getBookingsByGymId(String gymId) throws InvalidInputException;
}