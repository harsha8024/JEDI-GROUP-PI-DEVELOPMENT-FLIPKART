package com.flipfit.business;

import com.flipfit.bean.User;
import com.flipfit.bean.Gym;
import com.flipfit.bean.Slot;
import com.flipfit.bean.Booking;
import com.flipfit.database.LocalFileDatabase;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class GymCustomerServiceImpl implements GymCustomerInterface {

    public void register(User user) {
        GymUserServiceImpl userService = new GymUserServiceImpl();
        userService.register(user);
    }

    public void viewCenters(String cityInput) {
        List<Gym> allGyms = GymOwnerServiceImpl.getGymList(); 
        List<Gym> filteredGyms = allGyms.stream().filter(gym -> gym.getLocation() != null && gym.getLocation().equalsIgnoreCase(cityInput)).filter(Gym::isApproved).collect(Collectors.toList());
        if (filteredGyms.isEmpty()) {
            System.out.println("No approved gym centers found in " + cityInput);
        } else {
            System.out.println("\n--- Available Centers in " + cityInput + " ---");
            filteredGyms.forEach(g -> System.out.println("ID: " + g.getGymId() + " | Name: " + g.getGymName() + " | Address: " + g.getLocation()));
        }
    }

    public void viewSlotsForGym(String gymId, LocalDate date) {
        List<Slot> slots = LocalFileDatabase.loadSlots().stream().filter(slot -> slot.getGymId().equals(gymId)).collect(Collectors.toList());
        if (slots.isEmpty()) {
            System.out.println("No slots available for this gym.");
        } else {
            System.out.println("\n--- Available Slots for Gym " + gymId + " on " + date + " ---");
            for (Slot slot : slots) {
                String availability = slot.getAvailableSeats() > 0 ? "Available" : "Full";
                System.out.println("Slot ID: " + slot.getSlotId() + " | Time: " + slot.getStartTime() + " - " + slot.getEndTime() + " | Available: " + slot.getAvailableSeats() + "/" + slot.getCapacity() + " | Status: " + availability);
            }
        }
    }

    public List<Slot> getAvailableSlots(String gymId, LocalDate date) {
        return LocalFileDatabase.loadSlots().stream().filter(slot -> slot.getGymId().equals(gymId)).filter(slot -> slot.getAvailableSeats() > 0).collect(Collectors.toList());
    }

    public boolean bookSlot(String userId, String slotId, String gymId, LocalDate date) {
        List<Slot> slots = LocalFileDatabase.loadSlots();
        List<Booking> bookings = LocalFileDatabase.loadBookings();
        Slot targetSlot = slots.stream().filter(slot -> slot.getSlotId().equals(slotId)).findFirst().orElse(null);
        if (targetSlot == null) {
            System.out.println("Error: Slot not found!");
            return false;
        }
        if (targetSlot.getAvailableSeats() <= 0) {
            System.out.println("Error: This slot is fully booked!");
            return false;
        }
        final Slot finalTargetSlot = targetSlot;
        boolean conflictExists = bookings.stream().filter(b -> b.getUserId().equals(userId)).filter(b -> b.getBookingDate().equals(date)).filter(b -> b.getStatus().equals("ACTIVE")).anyMatch(b -> {
            Slot bookedSlot = slots.stream().filter(s -> s.getSlotId().equals(b.getSlotId())).findFirst().orElse(null);
            if (bookedSlot != null) {
                return !(finalTargetSlot.getEndTime().isBefore(bookedSlot.getStartTime()) || finalTargetSlot.getStartTime().isAfter(bookedSlot.getEndTime()));
            }
            return false;
        });
        if (conflictExists) {
            System.out.println("Error: You already have a booking at this time on " + date);
            return false;
        }
        String bookingId = LocalFileDatabase.generateBookingId();
        Booking newBooking = new Booking();
        newBooking.setBookingId(bookingId);
        newBooking.setUserId(userId);
        newBooking.setSlotId(slotId);
        newBooking.setGymId(gymId);
        newBooking.setBookingDate(date);
        newBooking.setStatus("ACTIVE");
        newBooking.setCreatedAt(LocalDateTime.now());
        targetSlot.setAvailableSeats(targetSlot.getAvailableSeats() - 1);
        LocalFileDatabase.saveBooking(newBooking);
        LocalFileDatabase.updateSlot(targetSlot);
        System.out.println("Successfully booked slot: " + slotId);
        return true;
    }

    public boolean cancelBooking(String bookingId, String userId) {
        List<Booking> bookings = LocalFileDatabase.loadBookings();
        Booking targetBooking = null;
        for (Booking booking : bookings) {
            if (booking.getBookingId().equals(bookingId) && booking.getUserId().equals(userId)) {
                targetBooking = booking;
                break;
            }
        }
        if (targetBooking == null) {
            System.out.println("Error: Booking not found.");
            return false;
        }
        if (!targetBooking.getStatus().equals("ACTIVE")) {
            System.out.println("Error: This booking is already cancelled.");
            return false;
        }
        List<Slot> slots = LocalFileDatabase.loadSlots();
        for (Slot slot : slots) {
            if (slot.getSlotId().equals(targetBooking.getSlotId())) {
                slot.setAvailableSeats(slot.getAvailableSeats() + 1);
                LocalFileDatabase.updateSlot(slot);
                break;
            }
        }
        targetBooking.setStatus("CANCELLED");
        LocalFileDatabase.updateBooking(targetBooking);
        System.out.println("Successfully cancelled booking: " + bookingId);
        return true;
    }

    public void viewMyBookings(String userId) {
        List<Booking> userBookings = LocalFileDatabase.loadBookings().stream().filter(booking -> booking.getUserId().equals(userId)).collect(Collectors.toList());
        if (userBookings.isEmpty()) {
            System.out.println("You have no bookings.");
        } else {
            System.out.println("\n--- Your Bookings ---");
            for (Booking booking : userBookings) {
                Slot slot = LocalFileDatabase.loadSlots().stream().filter(s -> s.getSlotId().equals(booking.getSlotId())).findFirst().orElse(null);
                String timeInfo = "";
                if (slot != null) {
                    timeInfo = " | Time: " + slot.getStartTime() + " - " + slot.getEndTime();
                }
                System.out.println("Booking ID: " + booking.getBookingId() + " | Gym: " + booking.getGymId() + " | Date: " + booking.getBookingDate() + timeInfo + " | Status: " + booking.getStatus());
            }
        }
    }
}
