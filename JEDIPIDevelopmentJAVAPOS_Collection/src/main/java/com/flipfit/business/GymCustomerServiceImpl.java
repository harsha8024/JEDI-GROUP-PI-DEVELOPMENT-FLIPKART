package com.flipfit.business;

import com.flipfit.bean.Gym;
import com.flipfit.bean.Slot;
import com.flipfit.bean.Booking;
import com.flipfit.dao.GymDAO;
import com.flipfit.dao.SlotDAO;
import com.flipfit.exception.BookingFailedException;
import com.flipfit.exception.SlotNotAvailableException;
import com.flipfit.exception.BookingCreationException;
import com.flipfit.exception.InvalidInputException;
import com.flipfit.exception.BookingCancellationException;
import com.flipfit.exception.BookingNotFoundException;
import com.flipfit.exception.PaymentFailedException;
import com.flipfit.exception.NotificationFailedException;
import com.flipfit.exception.GymNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class GymCustomerServiceImpl.
 *
 * @author team pi
 * @ClassName "GymCustomerServiceImpl"
 */
public class GymCustomerServiceImpl implements GymCustomerInterface {

    // DAOs for read-only browsing operations
    private GymDAO gymDAO;
    private SlotDAO slotDAO;
    
    // Business Services for complex logic delegation
    private BookingInterface bookingService;
    private PaymentInterface paymentService;
    private NotificationInterface notificationService;

    public GymCustomerServiceImpl() {
        this.gymDAO = new GymDAO();
        this.slotDAO = new SlotDAO();
        
        // Initialize the delegate services
        this.bookingService = new BookingServiceImpl();
        this.paymentService = new PaymentServiceImpl();
        this.notificationService = new NotificationServiceImpl();
    }

    // -------------------------------------------------------------------------
    // BROWSING OPERATIONS
    // -------------------------------------------------------------------------

    @Override
    public List<Gym> viewCenters(String cityInput) throws InvalidInputException, GymNotFoundException {
        if (cityInput == null || cityInput.isBlank()) {
            throw new InvalidInputException("City input cannot be empty.");
        }

        List<Gym> filteredGyms = gymDAO.getGymsByLocation(cityInput);
        if (filteredGyms.isEmpty()) {
            throw new GymNotFoundException("No approved gym centers found in " + cityInput);
        }
        System.out.println("\n--- Available Centers in " + cityInput + " ---");
        filteredGyms.forEach(g -> System.out.println(
            "ID: " + g.getGymId() + " | Name: " + g.getGymName() + " | Address: " + g.getLocation()
        ));
        return filteredGyms;
    }

    @Override
    public List<Slot> viewSlotsForGym(String gymId, LocalDate date) throws InvalidInputException, GymNotFoundException {
        if (gymId == null || gymId.isBlank() || date == null) {
            throw new InvalidInputException("Gym id and date must be provided.");
        }

        List<Slot> slots = slotDAO.getSlotsByGymId(gymId);
        
        // Filter only approved slots
        List<Slot> approvedSlots = new ArrayList<>();
        for (Slot slot : slots) {
            if (slot.isApproved()) {
                approvedSlots.add(slot);
            }
        }
        
        if (approvedSlots.isEmpty()) {
            throw new GymNotFoundException("No approved slots available for gym " + gymId + " on " + date);
        }
        
        System.out.println("\n--- Available Slots for Gym " + gymId + " on " + date + " ---");
        slots.forEach(slot -> {
            String availability = slot.getAvailableSeats() > 0 ? "Available" : "Full";
            System.out.println("Slot ID: " + slot.getSlotId() +
                    " | Time: " + slot.getStartTime() + " - " + slot.getEndTime() +
                    " | Available: " + slot.getAvailableSeats() + "/" + slot.getCapacity() +
                    " | Status: " + availability);
        });
        return approvedSlots;
    }

    @Override
    public List<Slot> getAvailableSlots(String gymId, LocalDate date) throws InvalidInputException {
        if (gymId == null || gymId.isBlank()) {
            throw new InvalidInputException("Invalid gym id.");
        }
        return slotDAO.getAvailableSlotsByGymId(gymId);
    }

    // -------------------------------------------------------------------------
    // BOOKING OPERATIONS (Delegated to specialized services)
    // -------------------------------------------------------------------------

    @Override
    public boolean bookSlot(String userId, String slotId, String gymId, LocalDate date) 
            throws BookingFailedException, SlotNotAvailableException, InvalidInputException, BookingCreationException {
        if (userId == null || userId.isBlank() || slotId == null || slotId.isBlank() || gymId == null || gymId.isBlank() || date == null) {
            throw new InvalidInputException("Invalid booking parameters.");
        }
        
        // 1. Validate Slot Availability and Approval
        Slot targetSlot = slotDAO.getSlotById(slotId);
        if (targetSlot == null) {
            throw new SlotNotAvailableException("Slot " + slotId + " does not exist.");
        }
        if (!targetSlot.isApproved()) {
            throw new BookingFailedException("Slot " + slotId + " is not approved by admin yet. Cannot book unapproved slots.");
        }
        if (targetSlot.getAvailableSeats() <= 0) {
            throw new SlotNotAvailableException("Slot " + slotId + " is full.");
        }

        // 2. Validate Gym is Approved
        Gym targetGym = gymDAO.getGymById(gymId);
        if (targetGym == null || !targetGym.isApproved()) {
            throw new BookingFailedException("Gym is not approved by admin yet. Cannot book slots from unapproved gyms.");
        }

        // 3. Create Booking via BookingService
        String bookingId = bookingService.addBooking(userId, slotId, gymId);
        
        if (bookingId != null) {
            // 4. Get slot price and process payment via PaymentService
            double slotPrice = targetSlot.getPrice() != null ? targetSlot.getPrice().doubleValue() : 500.0;
            boolean paymentSuccess;
            try {
                paymentSuccess = paymentService.processPayment(bookingId, slotPrice, "UPI");
            } catch (PaymentFailedException | InvalidInputException pfe) {
                try {
                    bookingService.cancelBooking(bookingId);
                } catch (BookingCancellationException bce) {
                    // Failed to rollback booking, log and continue to inform user
                    System.err.println("Error rolling back booking after payment failure: " + bce.getMessage());
                }
                throw new BookingFailedException("Payment failed. Booking cancelled.");
            }
            
            if (paymentSuccess) {
                // 5. Update Seats in DB
                slotDAO.decreaseAvailableSeats(slotId);
                
                // 6. Send Notification
                try {
                    notificationService.sendNotification(userId, 
                        "Booking Confirmed! ID: " + bookingId + " | Amount Paid: ₹" + slotPrice, 
                        "Booking Success"
                    );
                } catch (NotificationFailedException nfe) {
                    // Notification failure should not block the booking operation; log and continue
                    System.err.println("Warning: Could not send booking notification: " + nfe.getMessage());
                }
                
                System.out.println("Successfully booked slot: " + slotId + " | Amount: ₹" + slotPrice);
                return true;
            } else {
                // Rollback booking if payment fails
                try {
                    bookingService.cancelBooking(bookingId);
                } catch (BookingCancellationException bce) {
                    System.err.println("Error rolling back booking after payment failure: " + bce.getMessage());
                }
                throw new BookingFailedException("Payment failed. Booking cancelled.");
            }
        }
        
        throw new BookingFailedException("Unable to create booking.");
    }

    @Override
    public boolean cancelBooking(String bookingId, String userId) throws BookingFailedException, InvalidInputException {
        if (bookingId == null || bookingId.isBlank() || userId == null || userId.isBlank()) {
            throw new InvalidInputException("Invalid booking id or user id for cancellation.");
        }
        // 1. Validate Booking
        Booking targetBooking;
        try {
            targetBooking = bookingService.getBookingById(bookingId);
        } catch (BookingNotFoundException e) {
            throw new BookingFailedException("Booking not found or unauthorized.");
        }

        if (targetBooking == null || !targetBooking.getUserId().equals(userId)) {
            throw new BookingFailedException("Booking not found or unauthorized.");
        }
        if ("CANCELLED".equalsIgnoreCase(targetBooking.getStatus())) {
            throw new BookingFailedException("Booking is already cancelled.");
        }

        // 2. Perform Cancellation via BookingService
        try {
            if (bookingService.cancelBooking(bookingId)) {
                // 3. Restore Slot capacity
                slotDAO.increaseAvailableSeats(targetBooking.getSlotId());
                
                // 4. Notify User
                try {
                    notificationService.sendNotification(userId, 
                        "Your booking " + bookingId + " has been successfully cancelled.", 
                        "Cancellation Success"
                    );
                } catch (NotificationFailedException nfe) {
                    System.err.println("Warning: Could not send cancellation notification: " + nfe.getMessage());
                }
                
                System.out.println("Successfully cancelled booking: " + bookingId);
                return true;
            }
        } catch (BookingCancellationException e) {
            throw new BookingFailedException("Failed to cancel booking: " + e.getMessage());
        }

        return false;
    }

    @Override
    public List<Booking> viewMyBookings(String userId) throws InvalidInputException {
        if (userId == null || userId.isBlank()) throw new InvalidInputException("Invalid user id.");

        List<Booking> userBookings = bookingService.getBookingsByUserId(userId);
        
        if (userBookings.isEmpty()) {
            System.out.println("You have no bookings.");
        } else {
            System.out.println("\n--- Your Bookings ---");
            userBookings.forEach(booking -> {
                Slot slot = slotDAO.getSlotById(booking.getSlotId());
                String timeInfo = (slot != null) ? " | Time: " + slot.getStartTime() + " - " + slot.getEndTime() : "";

                System.out.println("Booking ID: " + booking.getBookingId() +
                        " | Gym: " + booking.getGymId() +
                        " | Date: " + booking.getBookingDate() + timeInfo +
                        " | Status: " + booking.getStatus());
            });
        }
        return userBookings;
    }
}