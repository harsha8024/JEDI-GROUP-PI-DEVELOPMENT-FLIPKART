package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.dao.BookingDAO;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingServiceImpl implements BookingInterface {

    private final BookingDAO bookingDAO = new BookingDAO();

    @Override
    public boolean checkBookingOverlap(String userId, LocalDate date, String slotId) {
        // Logic to check if user already has a booking at this time
        List<Booking> userBookings = bookingDAO.getBookingsByUserId(userId);
        
        // Check for any active booking on the same date
        return userBookings.stream()
                .anyMatch(b -> b.getBookingDate().equals(date) && 
                               !"CANCELLED".equalsIgnoreCase(b.getStatus()));
    }

    @Override
    public String addBooking(String userId, String slotId) {
        // Use your DAO's ID generator if it exists, otherwise use UUID
        String bookingId = UUID.randomUUID().toString(); 
        
        Booking newBooking = new Booking();
        newBooking.setBookingId(bookingId);
        newBooking.setUserId(userId);
        newBooking.setSlotId(slotId);
        newBooking.setBookingDate(LocalDate.now()); 
        newBooking.setStatus("CONFIRMED");
        newBooking.setCreatedAt(LocalDateTime.now());
        
        if(bookingDAO.saveBooking(newBooking)) {
            return bookingId;
        }
        return null;
    }

    @Override
    public boolean cancelBooking(String bookingId) {
        return bookingDAO.cancelBooking(bookingId);
    }

    @Override
    public Booking getBookingById(String bookingId) {
        return bookingDAO.getBookingById(bookingId);
    }

    @Override
    public List<Booking> getBookingsByUserId(String userId) {
        return bookingDAO.getBookingsByUserId(userId);
    }
    
    @Override
    public List<Booking> getBookingsByGymId(String gymId) {
        return bookingDAO.getBookingsByGymId(gymId);
    }
}