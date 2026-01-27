package com.flipfit.business;

import com.flipfit.bean.User;
import com.flipfit.bean.Gym;
import com.flipfit.bean.Slot;
import com.flipfit.bean.Booking;
import com.flipfit.dao.GymDAO;
import com.flipfit.dao.SlotDAO;
import com.flipfit.exception.BookingFailedException;
import com.flipfit.exception.SlotNotAvailableException;
import com.flipfit.dao.BookingDAO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class GymCustomerServiceImpl implements GymCustomerInterface {

    private GymDAO gymDAO;
    private SlotDAO slotDAO;
    private BookingDAO bookingDAO;

    public GymCustomerServiceImpl() {
        this.gymDAO = new GymDAO();
        this.slotDAO = new SlotDAO();
        this.bookingDAO = new BookingDAO();
    }

    public void register(User user) {
        GymUserServiceImpl userService = new GymUserServiceImpl();
        userService.register(user);
    }

    public void viewCenters(String cityInput) {
        List<Gym> filteredGyms = gymDAO.getGymsByLocation(cityInput);
        if (filteredGyms.isEmpty()) {
            System.out.println("No approved gym centers found in " + cityInput);
        } else {
            System.out.println("\n--- Available Centers in " + cityInput + " ---");
            filteredGyms.forEach(g -> System.out
                    .println("ID: " + g.getGymId() + " | Name: " + g.getGymName() + " | Address: " + g.getLocation()));
        }
    }

    public void viewSlotsForGym(String gymId, LocalDate date) {
        List<Slot> slots = slotDAO.getSlotsByGymId(gymId);
        if (slots.isEmpty()) {
            System.out.println("No slots available for this gym.");
        } else {
            System.out.println("\n--- Available Slots for Gym " + gymId + " on " + date + " ---");
            for (Slot slot : slots) {
                String availability = slot.getAvailableSeats() > 0 ? "Available" : "Full";
                System.out.println("Slot ID: " + slot.getSlotId() + " | Time: " + slot.getStartTime() + " - "
                        + slot.getEndTime() + " | Available: " + slot.getAvailableSeats() + "/" + slot.getCapacity()
                        + " | Status: " + availability);
            }
        }
    }

    public List<Slot> getAvailableSlots(String gymId, LocalDate date) {
        return slotDAO.getAvailableSlotsByGymId(gymId);
    }

    public boolean bookSlot(String userId, String slotId, String gymId, LocalDate date) 
            throws BookingFailedException, SlotNotAvailableException {
        
        Slot targetSlot = slotDAO.getSlotById(slotId); //
        if (targetSlot == null) {
            throw new SlotNotAvailableException("Error: Slot " + slotId + " does not exist."); //
        }
        if (targetSlot.getAvailableSeats() <= 0) {
            throw new SlotNotAvailableException("Error: Slot " + slotId + " is fully booked!"); //
        }

        // Logic for conflicts
        List<Booking> userBookings = bookingDAO.getBookingsByUserId(userId); //
        boolean conflict = userBookings.stream()
                .filter(b -> b.getBookingDate().equals(date) && !b.getStatus().equals("CANCELLED"))
                .anyMatch(b -> {
                    Slot s = slotDAO.getSlotById(b.getSlotId()); //
                    return s != null && !(targetSlot.getEndTime().isBefore(s.getStartTime()) || 
                                         targetSlot.getStartTime().isAfter(s.getEndTime()));
                });

        if (conflict) {
            throw new BookingFailedException("Time conflict: You already have a booking during this period."); //
        }

        Booking newBooking = new Booking();
        newBooking.setBookingId(bookingDAO.generateBookingId()); //
        newBooking.setUserId(userId);
        newBooking.setSlotId(slotId);
        newBooking.setGymId(gymId);
        newBooking.setBookingDate(date);
        newBooking.setStatus("CONFIRMED");
        newBooking.setCreatedAt(LocalDateTime.now());

        if (bookingDAO.saveBooking(newBooking)) { //
            slotDAO.decreaseAvailableSeats(slotId); //
            return true;
        } else {
            throw new BookingFailedException("Database error: Booking could not be saved."); //
        }
    }

    public boolean cancelBooking(String bookingId, String userId) throws BookingFailedException {
        Booking booking = bookingDAO.getBookingById(bookingId); //
        if (booking == null || !booking.getUserId().equals(userId)) {
            throw new BookingFailedException("Booking not found or access denied."); //
        }
        if (booking.getStatus().equals("CANCELLED")) {
            throw new BookingFailedException("Booking is already cancelled."); //
        }

        if (bookingDAO.cancelBooking(bookingId)) { //
            slotDAO.increaseAvailableSeats(booking.getSlotId()); //
            return true;
        }
        throw new BookingFailedException("System error: Cancellation failed."); //
    }

    public void viewMyBookings(String userId) {
        List<Booking> userBookings = bookingDAO.getBookingsByUserId(userId);
        if (userBookings.isEmpty()) {
            System.out.println("You have no bookings.");
        } else {
            System.out.println("\n--- Your Bookings ---");
            for (Booking booking : userBookings) {
                Slot slot = slotDAO.getSlotById(booking.getSlotId());
                String timeInfo = "";
                if (slot != null) {
                    timeInfo = " | Time: " + slot.getStartTime() + " - " + slot.getEndTime();
                }
                System.out.println("Booking ID: " + booking.getBookingId() + " | Gym: " + booking.getGymId()
                        + " | Date: " + booking.getBookingDate() + timeInfo + " | Status: " + booking.getStatus());
            }
        }
    }
}
