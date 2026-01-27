package com.flipfit.business;

import com.flipfit.bean.User;
import com.flipfit.exception.BookingFailedException;
import com.flipfit.exception.RegistrationFailedException;
import com.flipfit.exception.SlotNotAvailableException;
import com.flipfit.bean.Slot;
import java.time.LocalDate;
import java.util.List;

public interface GymCustomerInterface {
    void register(User user) throws RegistrationFailedException;
    void viewCenters(String city);
    void viewSlotsForGym(String gymId, LocalDate date);
    boolean bookSlot(String userId, String slotId, String gymId, LocalDate date) throws BookingFailedException, SlotNotAvailableException;
    boolean cancelBooking(String bookingId, String userId) throws BookingFailedException;
    void viewMyBookings(String userId);
    List<Slot> getAvailableSlots(String gymId, LocalDate date);
}