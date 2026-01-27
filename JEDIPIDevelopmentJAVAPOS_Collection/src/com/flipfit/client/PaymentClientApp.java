package com.flipfit.client;

import com.flipfit.bean.Payment;
import com.flipfit.bean.Booking;
import com.flipfit.bean.GymCustomer;
import com.flipfit.bean.Gym;
import com.flipfit.bean.Slot;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.Role;
import com.flipfit.dao.PaymentDAO;
import com.flipfit.dao.BookingDAO;
import com.flipfit.dao.CustomerDAO;
import com.flipfit.dao.GymDAO;
import com.flipfit.dao.SlotDAO;
import com.flipfit.dao.GymOwnerDAO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Payment DAO CRUD Operations Demo
 * Demonstrates Create, Read, Update, Delete operations
 */
public class PaymentClientApp {
  public static void main(String[] args) {
    PaymentDAO paymentDAO = new PaymentDAO();
    BookingDAO bookingDAO = new BookingDAO();
    CustomerDAO customerDAO = new CustomerDAO();
    GymDAO gymDAO = new GymDAO();
    SlotDAO slotDAO = new SlotDAO();
    GymOwnerDAO ownerDAO = new GymOwnerDAO();
    
    System.out.println("========================================");
    System.out.println("  PAYMENT DAO CRUD OPERATIONS DEMO");
    System.out.println("========================================\n");
    
    // Setup: Create required test data
    System.out.println("--- Setting up test data ---");
    
    // Create customer
    GymCustomer customer = new GymCustomer();
    String customerId = customerDAO.generateCustomerId();
    customer.setUserID(customerId);
    customer.setName("Payment Customer");
    customer.setEmail("payment.test@customer.com");
    customer.setPhoneNumber("1111111111");
    customer.setCity("Delhi");
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
    owner.setName("Payment Owner");
    owner.setEmail("payment.test@owner.com");
    owner.setPhoneNumber("2222222222");
    owner.setCity("Delhi");
    owner.setPassword("ownerpass");
    owner.setPanNumber("PPPPP1111P");
    owner.setAadharNumber("111111111111");
    Role ownerRole = new Role();
    ownerRole.setRoleName("OWNER");
    owner.setRole(ownerRole);
    ownerDAO.saveGymOwner(owner);
    System.out.println("✓ Owner created: " + ownerId);
    
    // Create gym
    Gym gym = new Gym();
    String gymId = gymDAO.generateGymId();
    gym.setGymId(gymId);
    gym.setGymName("Payment Test Gym");
    gym.setLocation("Connaught Place, Delhi");
    gym.setGymOwnerId(ownerId);
    gym.setApproved(true);
    gymDAO.saveGym(gym);
    System.out.println("✓ Gym created: " + gymId);
    
    // Create slot
    Slot slot = new Slot();
    String slotId = slotDAO.generateSlotId();
    slot.setSlotId(slotId);
    slot.setGymId(gymId);
    slot.setStartTime(LocalTime.of(10, 0));
    slot.setEndTime(LocalTime.of(11, 0));
    slot.setCapacity(15);
    slot.setAvailableSeats(15);
    slotDAO.saveSlot(slot);
    System.out.println("✓ Slot created: " + slotId);
    
    // Create booking
    Booking booking = new Booking();
    String bookingId = bookingDAO.generateBookingId();
    booking.setBookingId(bookingId);
    booking.setUserId(customerId);
    booking.setGymId(gymId);
    booking.setSlotId(slotId);
    booking.setBookingDate(LocalDate.now());
    booking.setStatus("CONFIRMED");
    booking.setCreatedAt(LocalDateTime.now());
    bookingDAO.saveBooking(booking);
    System.out.println("✓ Booking created: " + bookingId);
    
    // ==================== CREATE ====================
    System.out.println("\n--- CREATE Operation ---");
    Payment payment = new Payment();
    String paymentId = paymentDAO.generatePaymentId();
    payment.setPaymentId(paymentId);
    payment.setBookingId(bookingId);
    payment.setUserId(customerId);
    payment.setAmount(new BigDecimal("599.00"));
    payment.setPaymentMethod("Credit Card");
    payment.setPaymentStatus("SUCCESS");
    payment.setPaymentDate(LocalDateTime.now());
    
    boolean created = paymentDAO.savePayment(payment);
    if (created) {
      System.out.println("✓ Payment created successfully!");
      System.out.println("  Payment ID: " + payment.getPaymentId());
      System.out.println("  Booking ID: " + payment.getBookingId());
      System.out.println("  Customer ID: " + payment.getUserId());
      System.out.println("  Amount: ₹" + payment.getAmount());
      System.out.println("  Method: " + payment.getPaymentMethod());
      System.out.println("  Status: " + payment.getPaymentStatus());
    } else {
      System.out.println("✗ Failed to create payment.");
    }
    
    // ==================== READ ====================
    System.out.println("\n--- READ Operations ---");
    
    // Read by ID
    System.out.println("\n1. Read by ID:");
    Payment retrievedById = paymentDAO.getPaymentById(paymentId);
    if (retrievedById != null) {
      System.out.println("✓ Payment found by ID:");
      System.out.println("  Booking: " + retrievedById.getBookingId());
      System.out.println("  Amount: ₹" + retrievedById.getAmount());
      System.out.println("  Method: " + retrievedById.getPaymentMethod());
      System.out.println("  Status: " + retrievedById.getPaymentStatus());
    } else {
      System.out.println("✗ Payment not found by ID");
    }
    
    // Read by Booking ID
    System.out.println("\n2. Read by Booking ID:");
    Payment retrievedByBooking = paymentDAO.getPaymentByBookingId(bookingId);
    if (retrievedByBooking != null) {
      System.out.println("✓ Payment found for booking:");
      System.out.println("  Payment ID: " + retrievedByBooking.getPaymentId());
      System.out.println("  Amount: ₹" + retrievedByBooking.getAmount());
    } else {
      System.out.println("✗ Payment not found for booking");
    }
    
    // Read All Payments
    System.out.println("\n3. Read All Payments:");
    List<Payment> allPayments = paymentDAO.getAllPayments();
    System.out.println("✓ Total payments in database: " + allPayments.size());
    
    // Read Payments by Customer
    System.out.println("\n4. Read Payments by Customer ID:");
    List<Payment> customerPayments = paymentDAO.getPaymentsByUserId(customerId);
    System.out.println("✓ Total payments for customer: " + customerPayments.size());
    customerPayments.forEach(p -> 
      System.out.println("  - Payment: " + p.getPaymentId() + " | Amount: ₹" + p.getAmount() + " | Status: " + p.getPaymentStatus())
    );
    
    // Read Successful Payments
    System.out.println("\n5. Read Successful Payments by Customer:");
    List<Payment> successfulPayments = paymentDAO.getSuccessfulPaymentsByUserId(customerId);
    System.out.println("✓ Successful payments: " + successfulPayments.size());
    
    // Get Total Payment Amount
    System.out.println("\n6. Get Total Payment Amount:");
    BigDecimal totalAmount = paymentDAO.getTotalPaymentByUserId(customerId);
    System.out.println("✓ Total amount paid by customer: ₹" + totalAmount);
    
    // ==================== UPDATE ====================
    System.out.println("\n--- UPDATE Operation ---");
    boolean statusUpdated = paymentDAO.updatePaymentStatus(paymentId, "COMPLETED");
    if (statusUpdated) {
      System.out.println("✓ Payment status updated successfully!");
      
      // Verify update
      Payment updatedPayment = paymentDAO.getPaymentById(paymentId);
      System.out.println("  Updated Status: " + updatedPayment.getPaymentStatus());
    } else {
      System.out.println("✗ Failed to update payment status.");
    }
    
    // ==================== DELETE ====================
    System.out.println("\n--- DELETE Operation ---");
    System.out.println("Deleting payment with ID: " + paymentId);
    boolean deleted = paymentDAO.deletePayment(paymentId);
    if (deleted) {
      System.out.println("✓ Payment deleted successfully!");
      
      // Verify deletion
      Payment deletedPayment = paymentDAO.getPaymentById(paymentId);
      if (deletedPayment == null) {
        System.out.println("✓ Verified: Payment no longer exists in database");
      }
    } else {
      System.out.println("✗ Failed to delete payment.");
    }
    
    // Cleanup
    bookingDAO.deleteBooking(bookingId);
    slotDAO.deleteSlot(slotId);
    gymDAO.deleteGym(gymId);
    ownerDAO.deleteGymOwner(ownerId);
    customerDAO.deleteCustomer(customerId);
    
    System.out.println("\n========================================");
    System.out.println("  CRUD OPERATIONS COMPLETED");
    System.out.println("========================================");
  }
}
