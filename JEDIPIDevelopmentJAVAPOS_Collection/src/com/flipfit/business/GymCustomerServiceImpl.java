package com.flipfit.business;

import com.flipfit.bean.Gym;
import com.flipfit.bean.Slot;
import com.flipfit.bean.Booking;
import com.flipfit.dao.GymDAO;
import com.flipfit.dao.SlotDAO;
import com.flipfit.exception.BookingFailedException;
import com.flipfit.exception.SlotNotAvailableException;

import java.time.LocalDate;
import java.util.List;

public class GymCustomerServiceImpl implements GymCustomerInterface {

    // DAOs for read-only browsing operations
    private GymDAO gymDAO;
    private SlotDAO slotDAO;
    
    // New Business Services for complex logic
    private BookingInterface bookingService;
    private PaymentInterface paymentService;
    private NotificationInterface notificationService;

    public GymCustomerServiceImpl() {
        this.gymDAO = new GymDAO();
        this.slotDAO = new SlotDAO();
        
        // Initialize the new delegate services
        this.bookingService = new BookingServiceImpl();
        this.paymentService = new PaymentServiceImpl();
        this.notificationService = new NotificationServiceImpl();
    }

    // -------------------------------------------------------------------------
    // BROWSING OPERATIONS (Kept here for Customer Dashboard View)
    // -------------------------------------------------------------------------

    @Override
    public void viewCenters(String cityInput) {
        List<Gym> filteredGyms = gymDAO.getGymsByLocation(cityInput);
        if (filteredGyms.isEmpty()) {
            System.out.println("No approved gym centers found in " + cityInput);
        } else {
            System.out.println("\n--- Available Centers in " + cityInput + " ---");
            filteredGyms.forEach(g -> System.out.println(
                "ID: " + g.getGymId() + " | Name: " + g.getGymName() + " | Address: " + g.getLocation()
            ));
        }
    }

    @Override
    public void viewSlotsForGym(String gymId, LocalDate date) {
        List<Slot> slots = slotDAO.getSlotsByGymId(gymId);
        if (slots.isEmpty()) {
            System.out.println("No slots available for this gym.");
        } else {
            System.out.println("\n--- Available Slots for Gym " + gymId + " on " + date + " ---");
            for (Slot slot : slots) {
                String availability = slot.getAvailableSeats() > 0 ? "Available" : "Full";
                System.out.println("Slot ID: " + slot.getSlotId() + 
                                   " | Time: " + slot.getStartTime() + " - " + slot.getEndTime() + 
                                   " | Available: " + slot.getAvailableSeats() + "/" + slot.getCapacity() + 
                                   " | Status: " + availability);
            }
        }
    }

    @Override
    public List<Slot> getAvailableSlots(String gymId, LocalDate date) {
        return slotDAO.getAvailableSlotsByGymId(gymId);
    }

    // -------------------------------------------------------------------------
    // COMPLEX TRANSACTIONS (Delegated to specialized services)
    // -------------------------------------------------------------------------

    @Override
    public boolean bookSlot(String userId, String slotId, String gymId, LocalDate date) 
            throws BookingFailedException, SlotNotAvailableException {
        
        // 1. Validate Slot Availability (Read Check)
        Slot targetSlot = slotDAO.getSlotById(slotId);
        if (targetSlot == null || targetSlot.getAvailableSeats() <= 0) {
            throw new SlotNotAvailableException("Slot " + slotId + " is full or does not exist.");
        }

        // 2. Create Booking (Delegate to BookingService)
        // BookingService handles conflict checks internally now
        String bookingId = bookingService.addBooking(userId, slotId);
        
        if (bookingId != null) {
            // 3. Process Payment (Delegate to PaymentService)
            boolean paymentSuccess = paymentService.processPayment(bookingId, 500.0, "UPI");
            
            if (paymentSuccess) {
                // 4. Finalize Slot (Update Seats)
                slotDAO.decreaseAvailableSeats(slotId);
                
                // 5. Send Notification (Delegate to NotificationService)
                notificationService.sendNotification(userId, 
                    "Booking Confirmed! ID: " + bookingId, 
                    "Booking Success"
                );
                
                System.out.println("Successfully booked slot: " + slotId);
                return true;
            } else {
                // Rollback booking if payment fails
                bookingService.cancelBooking(bookingId);
                throw new BookingFailedException("Payment failed. Booking cancelled.");
            }
        }
        
        throw new BookingFailedException("Unable to create booking.");
    }

    @Override
    public boolean cancelBooking(String bookingId, String userId) throws BookingFailedException {
        // 1. Validate Booking ownership
        Booking targetBooking = bookingService.getBookingById(bookingId);

        if (targetBooking == null || !targetBooking.getUserId().equals(userId)) {
            throw new BookingFailedException("Booking not found or you are not authorized to cancel it.");
        }
        if ("CANCELLED".equalsIgnoreCase(targetBooking.getStatus())) {
            throw new BookingFailedException("Booking is already cancelled.");
        }

        // 2. Perform Cancellation (Delegate to BookingService)
        if (bookingService.cancelBooking(bookingId)) {
            // 3. Restore Seat
            slotDAO.increaseAvailableSeats(targetBooking.getSlotId());
            
            // 4. Process Refund (Optional - Delegate to PaymentService)
            // String paymentId = paymentService.getPaymentByBooking(bookingId).getId();
            // paymentService.refundPayment(paymentId);

            // 5. Send Notification (Delegate to NotificationService)
            notificationService.sendNotification(userId, 
                "Your booking " + bookingId + " has been successfully cancelled.", 
                "Cancellation Success"
            );
            
            System.out.println("Successfully cancelled booking: " + bookingId);
            return true;
        }

        return false;
    }

    @Override
    public void viewMyBookings(String userId) {
        // Delegate fetching logic to BookingService
        List<Booking> userBookings = bookingService.getBookingsByUserId(userId);
        
        if (userBookings.isEmpty()) {
            System.out.println("You have no bookings.");
        } else {
            System.out.println("\n--- Your Bookings ---");
            for (Booking booking : userBookings) {
                Slot slot = slotDAO.getSlotById(booking.getSlotId());
                String timeInfo = (slot != null) ? " | Time: " + slot.getStartTime() + " - " + slot.getEndTime() : "";
                
                System.out.println("Booking ID: " + booking.getBookingId() + 
                                   " | Gym: " + booking.getGymId() +
                                   " | Date: " + booking.getBookingDate() + timeInfo + 
                                   " | Status: " + booking.getStatus());
            }
        }
    }
}