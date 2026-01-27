package com.flipfit.client;

import com.flipfit.bean.Booking;
import com.flipfit.bean.GymCustomer;
import com.flipfit.bean.Gym;
import com.flipfit.bean.Slot;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.Role;
import com.flipfit.dao.BookingDAO;
import com.flipfit.dao.CustomerDAO;
import com.flipfit.dao.GymDAO;
import com.flipfit.dao.SlotDAO;
import com.flipfit.dao.GymOwnerDAO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Booking DAO CRUD Operations Demo
 * Demonstrates Create, Read, Update, Delete operations
 */
public class BookingClientApp {
  public static void main(String[] args) {
    BookingDAO bookingDAO = new BookingDAO();
    CustomerDAO customerDAO = new CustomerDAO();
    GymDAO gymDAO = new GymDAO();
    SlotDAO slotDAO = new SlotDAO();
    GymOwnerDAO ownerDAO = new GymOwnerDAO();
    
    System.out.println("========================================");
    System.out.println("  BOOKING DAO CRUD OPERATIONS DEMO");
    System.out.println("========================================\n");
    
    // Setup: Create customer, owner, gym, and slot
    System.out.println("--- Setting up test data ---");
    
    // Create customer
    GymCustomer customer = new GymCustomer();
    String customerId = customerDAO.generateCustomerId();
    customer.setUserID(customerId);
    customer.setName("Test Customer");
    customer.setEmail("test.booking@customer.com");
    customer.setPhoneNumber("1234567890");
    customer.setCity("Mumbai");
    customer.setPassword("pass123");
    Role customerRole = new Role();
    customerRole.setRoleName("CUSTOMER");
    customer.setRole(customerRole);
    customerDAO.saveCustomer(customer);
    System.out.println("✓ Customer created: " + customerId);
    
    // Create owner
    GymOwner owner = new GymOwner();
    String ownerId = ownerDAO.generateOwnerId();
    owner.setUserID(ownerId);
    owner.setName("Test Owner");
    owner.setEmail("test.booking@owner.com");
    owner.setPhoneNumber("9876543210");
    owner.setCity("Mumbai");
    owner.setPassword("ownerpass");
    owner.setPanNumber("ABCDE1234F");
    owner.setAadharNumber("123456789012");
    Role ownerRole = new Role();
    ownerRole.setRoleName("OWNER");
    owner.setRole(ownerRole);
    ownerDAO.saveGymOwner(owner);
    System.out.println("✓ Owner created: " + ownerId);
    
    // Create gym
    Gym gym = new Gym();
    String gymId = gymDAO.generateGymId();
    gym.setGymId(gymId);
    gym.setGymName("Test Gym");
    gym.setLocation("Andheri, Mumbai");
    gym.setGymOwnerId(ownerId);
    gym.setApproved(true);
    gymDAO.saveGym(gym);
    System.out.println("✓ Gym created: " + gymId);
    
    // Create slot
    Slot slot = new Slot();
    String slotId = slotDAO.generateSlotId();
    slot.setSlotId(slotId);
    slot.setGymId(gymId);
    slot.setStartTime(LocalTime.of(9, 0));
    slot.setEndTime(LocalTime.of(10, 0));
    slot.setCapacity(20);
    slot.setAvailableSeats(20);
    slotDAO.saveSlot(slot);
    System.out.println("✓ Slot created: " + slotId);
    
    // ==================== CREATE ====================
    System.out.println("\n--- CREATE Operation ---");
    Booking booking = new Booking();
    String bookingId = bookingDAO.generateBookingId();
    booking.setBookingId(bookingId);
    booking.setUserId(customerId);
    booking.setGymId(gymId);
    booking.setSlotId(slotId);
    booking.setBookingDate(LocalDate.now());
    booking.setStatus("CONFIRMED");
    booking.setCreatedAt(LocalDateTime.now());
    
    boolean created = bookingDAO.saveBooking(booking);
    if (created) {
      System.out.println("✓ Booking created successfully!");
      System.out.println("  Booking ID: " + booking.getBookingId());
      System.out.println("  Customer ID: " + booking.getUserId());
      System.out.println("  Gym ID: " + booking.getGymId());
      System.out.println("  Slot ID: " + booking.getSlotId());
      System.out.println("  Date: " + booking.getBookingDate());
      System.out.println("  Status: " + booking.getStatus());
    } else {
      System.out.println("✗ Failed to create booking.");
    }
    
    // ==================== READ ====================
    System.out.println("\n--- READ Operations ---");
    
    // Read by ID
    System.out.println("\n1. Read by ID:");
    Booking retrievedById = bookingDAO.getBookingById(bookingId);
    if (retrievedById != null) {
      System.out.println("✓ Booking found by ID:");
      System.out.println("  Customer: " + retrievedById.getUserId());
      System.out.println("  Gym: " + retrievedById.getGymId());
      System.out.println("  Date: " + retrievedById.getBookingDate());
      System.out.println("  Status: " + retrievedById.getStatus());
    } else {
      System.out.println("✗ Booking not found by ID");
    }
    
    // Read All Bookings
    System.out.println("\n2. Read All Bookings:");
    List<Booking> allBookings = bookingDAO.getAllBookings();
    System.out.println("✓ Total bookings in database: " + allBookings.size());
    
    // Read Bookings by Customer
    System.out.println("\n3. Read Bookings by Customer ID:");
    List<Booking> customerBookings = bookingDAO.getBookingsByUserId(customerId);
    System.out.println("✓ Total bookings for customer: " + customerBookings.size());
    customerBookings.forEach(b -> 
      System.out.println("  - Booking: " + b.getBookingId() + " | Status: " + b.getStatus())
    );
    
    // Read Bookings by Gym
    System.out.println("\n4. Read Bookings by Gym ID:");
    List<Booking> gymBookings = bookingDAO.getBookingsByGymId(gymId);
    System.out.println("✓ Total bookings for gym: " + gymBookings.size());
    
    // Read Bookings by Slot
    System.out.println("\n5. Read Bookings by Slot ID:");
    List<Booking> slotBookings = bookingDAO.getBookingsBySlotId(slotId);
    System.out.println("✓ Total bookings for slot: " + slotBookings.size());
    
    // Read Active Bookings
    System.out.println("\n6. Read Active Bookings by Customer:");
    List<Booking> activeBookings = bookingDAO.getActiveBookingsByUserId(customerId);
    System.out.println("✓ Active bookings: " + activeBookings.size());
    
    // ==================== UPDATE ====================
    System.out.println("\n--- UPDATE Operation ---");
    if (retrievedById != null) {
      retrievedById.setStatus("COMPLETED");
      
      boolean updated = bookingDAO.updateBooking(retrievedById);
      if (updated) {
        System.out.println("✓ Booking updated successfully!");
        
        // Verify update
        Booking updatedBooking = bookingDAO.getBookingById(bookingId);
        System.out.println("  Updated Status: " + updatedBooking.getStatus());
      } else {
        System.out.println("✗ Failed to update booking.");
      }
    }
    
    // Cancel Booking
    System.out.println("\n--- CANCEL Operation ---");
    boolean cancelled = bookingDAO.cancelBooking(bookingId);
    if (cancelled) {
      System.out.println("✓ Booking cancelled successfully!");
      
      // Verify cancellation
      Booking cancelledBooking = bookingDAO.getBookingById(bookingId);
      System.out.println("  Status after cancellation: " + cancelledBooking.getStatus());
    } else {
      System.out.println("✗ Failed to cancel booking.");
    }
    
    // ==================== DELETE ====================
    System.out.println("\n--- DELETE Operation ---");
    System.out.println("Deleting booking with ID: " + bookingId);
    boolean deleted = bookingDAO.deleteBooking(bookingId);
    if (deleted) {
      System.out.println("✓ Booking deleted successfully!");
      
      // Verify deletion
      Booking deletedBooking = bookingDAO.getBookingById(bookingId);
      if (deletedBooking == null) {
        System.out.println("✓ Verified: Booking no longer exists in database");
      }
    } else {
      System.out.println("✗ Failed to delete booking.");
    }
    
    // Cleanup
    slotDAO.deleteSlot(slotId);
    gymDAO.deleteGym(gymId);
    ownerDAO.deleteGymOwner(ownerId);
    customerDAO.deleteCustomer(customerId);
    
    System.out.println("\n========================================");
    System.out.println("  CRUD OPERATIONS COMPLETED");
    System.out.println("========================================");
  }
}
