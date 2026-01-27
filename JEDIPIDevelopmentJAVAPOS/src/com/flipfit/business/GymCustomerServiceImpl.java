package com.flipfit.business;

import com.flipfit.bean.User;
import com.flipfit.bean.Gym;
import com.flipfit.bean.Slot;
import com.flipfit.bean.Booking;
import com.flipfit.dao.GymDAO;
import com.flipfit.dao.SlotDAO;
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

    public boolean bookSlot(String userId, String slotId, String gymId, LocalDate date) {
        Slot targetSlot = slotDAO.getSlotById(slotId);
        if (targetSlot == null) {
            System.out.println("Error: Slot not found!");
            return false;
        }
        if (targetSlot.getAvailableSeats() <= 0) {
            System.out.println("Error: This slot is fully booked!");
            return false;
        }

        // Check for conflicts in the database
        List<Booking> userBookings = bookingDAO.getBookingsByUserId(userId);
        boolean conflictExists = userBookings.stream()
                .filter(b -> b.getBookingDate().equals(date))
                .filter(b -> !b.getStatus().equals("CANCELLED"))
                .anyMatch(b -> {
                    Slot bookedSlot = slotDAO.getSlotById(b.getSlotId());
                    if (bookedSlot != null) {
                        return !(targetSlot.getEndTime().isBefore(bookedSlot.getStartTime())
                                || targetSlot.getStartTime().isAfter(bookedSlot.getEndTime()));
                    }
                    return false;
                });

        if (conflictExists) {
            System.out.println("Error: You already have a booking at this time on " + date);
            return false;
        }

        String bookingId = bookingDAO.generateBookingId();
        Booking newBooking = new Booking();
        newBooking.setBookingId(bookingId);
        newBooking.setUserId(userId);
        newBooking.setSlotId(slotId);
        newBooking.setGymId(gymId);
        newBooking.setBookingDate(date);
        newBooking.setStatus("CONFIRMED");
        newBooking.setCreatedAt(LocalDateTime.now());

        if (bookingDAO.saveBooking(newBooking)) {
            slotDAO.decreaseAvailableSeats(slotId);
            System.out.println("Successfully booked slot: " + slotId + " (Booking ID: " + bookingId + ")");
            return true;
        }

        System.err.println("Booking failed!");
        return false;
    }

    public boolean cancelBooking(String bookingId, String userId) {
        Booking targetBooking = bookingDAO.getBookingById(bookingId);

        if (targetBooking == null || !targetBooking.getUserId().equals(userId)) {
            System.out.println("Error: Booking not found.");
            return false;
        }
        if (targetBooking.getStatus().equals("CANCELLED")) {
            System.out.println("Error: This booking is already cancelled.");
            return false;
        }

        if (bookingDAO.cancelBooking(bookingId)) {
            slotDAO.increaseAvailableSeats(targetBooking.getSlotId());
            System.out.println("Successfully cancelled booking: " + bookingId);
            return true;
        }

        System.err.println("Cancellation failed!");
        return false;
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
