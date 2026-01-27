package com.flipfit.business;

import com.flipfit.bean.User;
import com.flipfit.bean.Slot;
import java.time.LocalDate;
import java.util.List;

public interface GymCustomerInterface {
    void register(User user); 
    void viewCenters(String city);
    void viewSlotsForGym(String gymId, LocalDate date);
    boolean bookSlot(String userId, String slotId, String gymId, LocalDate date);
    boolean cancelBooking(String bookingId, String userId);
    void viewMyBookings(String userId);
    List<Slot> getAvailableSlots(String gymId, LocalDate date);
}