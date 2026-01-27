package com.flipfit.client;

import com.flipfit.bean.Notification;
import com.flipfit.bean.GymCustomer;
import com.flipfit.bean.Role;
import com.flipfit.dao.NotificationDAO;
import com.flipfit.dao.CustomerDAO;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Notification DAO CRUD Operations Demo
 * Demonstrates Create, Read, Update, Delete operations
 */
public class NotificationClientApp {
  public static void main(String[] args) {
    NotificationDAO notificationDAO = new NotificationDAO();
    CustomerDAO customerDAO = new CustomerDAO();
    
    System.out.println("========================================");
    System.out.println("  NOTIFICATION DAO CRUD OPERATIONS");
    System.out.println("========================================\n");
    
    // Setup: Create a customer
    System.out.println("--- Setting up test data ---");
    GymCustomer customer = new GymCustomer();
    String customerId = customerDAO.generateCustomerId();
    customer.setUserID(customerId);
    customer.setName("Notification Customer");
    customer.setEmail("notification.test@customer.com");
    customer.setPhoneNumber("3333333333");
    customer.setCity("Kolkata");
    customer.setPassword("pass123");
    Role customerRole = new Role();
    customerRole.setRoleName("CUSTOMER");
    customer.setRole(customerRole);
    customerDAO.saveCustomer(customer);
    System.out.println("✓ Customer created: " + customerId);
    
    // ==================== CREATE ====================
    System.out.println("\n--- CREATE Operation ---");
    Notification notification = new Notification();
    String notificationId = notificationDAO.generateNotificationId();
    notification.setNotificationId(notificationId);
    notification.setUserId(customerId);
    notification.setTitle("Welcome to FlipFit!");
    notification.setMessage("Thank you for registering. Start booking your gym slots now.");
    notification.setRead(false);
    notification.setCreatedAt(LocalDateTime.now());
    
    boolean created = notificationDAO.saveNotification(notification);
    if (created) {
      System.out.println("✓ Notification created successfully!");
      System.out.println("  Notification ID: " + notification.getNotificationId());
      System.out.println("  User ID: " + notification.getUserId());
      System.out.println("  Title: " + notification.getTitle());
      System.out.println("  Message: " + notification.getMessage());
      System.out.println("  Read: " + notification.isRead());
    } else {
      System.out.println("✗ Failed to create notification.");
    }
    
    // Create another notification
    Notification notification2 = new Notification();
    String notificationId2 = notificationDAO.generateNotificationId();
    notification2.setNotificationId(notificationId2);
    notification2.setUserId(customerId);
    notification2.setTitle("Booking Confirmed");
    notification2.setMessage("Your gym booking for tomorrow at 9:00 AM has been confirmed.");
    notification2.setRead(false);
    notification2.setCreatedAt(LocalDateTime.now());
    notificationDAO.saveNotification(notification2);
    System.out.println("✓ Second notification created: " + notificationId2);
    
    // ==================== READ ====================
    System.out.println("\n--- READ Operations ---");
    
    // Read by ID
    System.out.println("\n1. Read by ID:");
    Notification retrievedById = notificationDAO.getNotificationById(notificationId);
    if (retrievedById != null) {
      System.out.println("✓ Notification found by ID:");
      System.out.println("  Title: " + retrievedById.getTitle());
      System.out.println("  Message: " + retrievedById.getMessage());
      System.out.println("  Read Status: " + retrievedById.isRead());
      System.out.println("  Created: " + retrievedById.getCreatedAt());
    } else {
      System.out.println("✗ Notification not found by ID");
    }
    
    // Read All Notifications
    System.out.println("\n2. Read All Notifications:");
    List<Notification> allNotifications = notificationDAO.getAllNotifications();
    System.out.println("✓ Total notifications in database: " + allNotifications.size());
    
    // Read Notifications by User
    System.out.println("\n3. Read Notifications by User ID:");
    List<Notification> userNotifications = notificationDAO.getNotificationsByUserId(customerId);
    System.out.println("✓ Total notifications for user: " + userNotifications.size());
    userNotifications.forEach(n -> 
      System.out.println("  - " + n.getTitle() + " | Read: " + n.isRead())
    );
    
    // Read Unread Notifications
    System.out.println("\n4. Read Unread Notifications:");
    List<Notification> unreadNotifications = notificationDAO.getUnreadNotificationsByUserId(customerId);
    System.out.println("✓ Unread notifications: " + unreadNotifications.size());
    unreadNotifications.forEach(n -> 
      System.out.println("  - " + n.getTitle())
    );
    
    // Get Unread Count
    System.out.println("\n5. Get Unread Notification Count:");
    int unreadCount = notificationDAO.getUnreadNotificationCount(customerId);
    System.out.println("✓ Unread count: " + unreadCount);
    
    // ==================== UPDATE ====================
    System.out.println("\n--- UPDATE Operations ---");
    
    // Mark Single Notification as Read
    System.out.println("\n1. Mark single notification as read:");
    boolean markedRead = notificationDAO.markAsRead(notificationId);
    if (markedRead) {
      System.out.println("✓ Notification marked as read successfully!");
      
      // Verify
      Notification readNotification = notificationDAO.getNotificationById(notificationId);
      System.out.println("  Read Status: " + readNotification.isRead());
      
      // Check updated unread count
      int newUnreadCount = notificationDAO.getUnreadNotificationCount(customerId);
      System.out.println("  New unread count: " + newUnreadCount);
    } else {
      System.out.println("✗ Failed to mark notification as read.");
    }
    
    // Mark All Notifications as Read
    System.out.println("\n2. Mark all notifications as read:");
    boolean allMarkedRead = notificationDAO.markAllAsReadForUser(customerId);
    if (allMarkedRead) {
      System.out.println("✓ All notifications marked as read successfully!");
      
      // Verify
      int finalUnreadCount = notificationDAO.getUnreadNotificationCount(customerId);
      System.out.println("  Final unread count: " + finalUnreadCount);
    } else {
      System.out.println("✗ Failed to mark all notifications as read.");
    }
    
    // ==================== DELETE ====================
    System.out.println("\n--- DELETE Operations ---");
    
    // Delete Single Notification
    System.out.println("\n1. Delete single notification:");
    System.out.println("Deleting notification with ID: " + notificationId);
    boolean deleted = notificationDAO.deleteNotification(notificationId);
    if (deleted) {
      System.out.println("✓ Notification deleted successfully!");
      
      // Verify deletion
      Notification deletedNotif = notificationDAO.getNotificationById(notificationId);
      if (deletedNotif == null) {
        System.out.println("✓ Verified: Notification no longer exists");
      }
    } else {
      System.out.println("✗ Failed to delete notification.");
    }
    
    // Delete All Notifications for User
    System.out.println("\n2. Delete all notifications for user:");
    boolean allDeleted = notificationDAO.deleteAllNotificationsForUser(customerId);
    if (allDeleted) {
      System.out.println("✓ All notifications deleted for user!");
      
      // Verify
      List<Notification> remainingNotifs = notificationDAO.getNotificationsByUserId(customerId);
      System.out.println("  Remaining notifications: " + remainingNotifs.size());
    } else {
      System.out.println("✗ Failed to delete notifications.");
    }
    
    // Cleanup
    customerDAO.deleteCustomer(customerId);
    
    System.out.println("\n========================================");
    System.out.println("  CRUD OPERATIONS COMPLETED");
    System.out.println("========================================");
  }
}
