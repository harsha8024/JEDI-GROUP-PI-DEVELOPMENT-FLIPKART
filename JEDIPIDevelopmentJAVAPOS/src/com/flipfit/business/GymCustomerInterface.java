package com.flipfit.business;

import com.flipfit.bean.User;

public interface GymCustomerInterface {
    void register(User user); 
    void viewCenters(String city);
    boolean bookSlot(String slotId);
    boolean cancelBooking(String bookingId);
}