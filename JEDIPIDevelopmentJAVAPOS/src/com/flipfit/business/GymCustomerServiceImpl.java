package com.flipfit.business;
import com.flipfit.bean.Gym;
import com.flipfit.bean.Booking;
import com.flipfit.bean.User;
import com.flipfit.bean.Slot;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GymCustomerServiceImpl implements GymCustomerInterface {
    
    // Collections to store our data in-memory
    private static List<Gym> gymCenters = new ArrayList<>();
    private static List<Booking> customerBookings = new ArrayList<>();
    private static List<Slot> allSlots = new ArrayList<>();

    // Hardcoded data initialization
    static {
        gymCenters.add(new Gym("G101", "FlipFit Bellandur", "Bangalore", true));
        gymCenters.add(new Gym("G102", "FlipFit Koramangala", "Bangalore", true));
        gymCenters.add(new Gym("G103", "FlipFit Andheri", "Mumbai", true));
        
        // Example slot for testing
        allSlots.add(new Slot("S1", "G101", java.time.LocalTime.of(6, 0), java.time.LocalTime.of(7, 0), 20, 15));
    }

    @Override
    public void viewCenters(String city) {
        System.out.println("\n--- Available Gyms in " + city + " ---");
        List<Gym> filtered = gymCenters.stream()
            .filter(g -> g.getLocation().equalsIgnoreCase(city) && g.isApproved())
            .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            System.out.println("No approved gyms found in this city.");
        } else {
            filtered.forEach(g -> System.out.println("Gym ID: " + g.getGymId() + " | Name: " + g.getGymName()));
        }
    }

    @Override
    public void viewAvailableSlots(String gymId, String date) {
        System.out.println("Fetching slots for Gym: " + gymId + " on " + date);
        allSlots.stream()
            .filter(s -> s.getGymId().equals(gymId))
            .forEach(s -> System.out.println(s.toString()));

    }

    @Override
    public boolean bookSlot(String slotId) {
        // 1. First, find the slot to know which gym it belongs to
        Slot selectedSlot = allSlots.stream()
                .filter(s -> s.getSlotId().equals(slotId))
                .findFirst()
                .orElse(null);

        if (selectedSlot == null) {
            System.out.println("Error: Slot ID " + slotId + " does not exist.");
            return false;
        }

        String gymId = selectedSlot.getGymId();

        // 2. Check if the Gym ID exists in the central gym list (from OwnerService)
        // Reusing the shared static list to ensure we are looking at real data
        boolean gymExists = GymOwnerServiceImpl.getGymList().stream()
                .anyMatch(g -> g.getGymId().equals(gymId));

        if (!gymExists) {
            System.out.println("Error: The gym associated with this slot (ID: " + gymId + ") no longer exists.");
            return false;
        }

        // 3. Create a new Booking object since validation passed
        Booking newBooking = new Booking();
        newBooking.setBookingId("B" + (customerBookings.size() + 1));
        newBooking.setSlotId(slotId);
        newBooking.setGymId(gymId); // Link the gym ID to the booking
        newBooking.setStatus("CONFIRMED");
        newBooking.setBookingDate(java.time.LocalDate.now()); // Default to today for testing
        
        customerBookings.add(newBooking);
        System.out.println("Booking successful! Your Booking ID: " + newBooking.getBookingId());
        return true;
    }

    @Override
    public void viewMyBookings() {
        System.out.println("\n--- Your Confirmed Bookings ---");
        
        // Check if the Collection is empty
        if (customerBookings.isEmpty()) {
            System.out.println("No bookings found. Go to 'Book a Slot' to make a reservation.");
        } else {
            // Use a loop or forEach to display each Booking bean in the List
            for (Booking b : customerBookings) {
                System.out.println("Booking ID: " + b.getBookingId() + 
                                   " | Slot ID: " + b.getSlotId() + 
                                   " | Status: " + b.getStatus());
            }
        }
    }
    
    @Override
    public void viewPlanByDay(String date) {
        System.out.println("\n--- Your Gym Plan for " + date + " ---");
        
        try {
            // 1. Convert String input to LocalDate
            java.time.LocalDate searchDate = java.time.LocalDate.parse(date);
            boolean found = false;

            // 2. Iterate through your static ArrayList
            for (com.flipfit.bean.Booking b : customerBookings) {
                // Compare the LocalDate objects
                if (b.getBookingDate() != null && b.getBookingDate().equals(searchDate)) {
                    System.out.println("Booking ID: " + b.getBookingId() + 
                                       " | Slot ID: " + b.getSlotId() + 
                                       " | Status: " + b.getStatus());
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No bookings found for " + date);
            }
        } catch (java.time.format.DateTimeParseException e) {
            System.out.println("Invalid date format! Please use YYYY-MM-DD.");
        }

    }

    @Override
    public boolean cancelBooking(String bookingId) {
        // removeIf returns true if a booking was actually found and removed
        boolean removed = customerBookings.removeIf(b -> b.getBookingId().equals(bookingId));

        
        if (removed) {
            System.out.println("Success: Booking " + bookingId + " has been removed from the system.");
        } else {
            System.out.println("Error: No booking found with ID " + bookingId);
        }
        
        return removed;
    }
    
    @Override
    public boolean updateProfile(String email, String name, String phone, String city) {
        System.out.println("Updating profile for " + email + " in Customer Service...");
        
        System.out.println("New Details - Name: " + name + ", City: " + city);
        
        return true; // Return true to indicate the Collection "updated"
    }
    
    @Override
    public void logout() {
        System.out.println("\nLogging out... Session data cleared from memory.");
    }

    @Override
    public void register() {
        System.out.println("Customer registration handled by GymUserService.");

    }
}