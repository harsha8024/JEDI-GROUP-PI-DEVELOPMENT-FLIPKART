package com.flipfit.utils;

import com.flipfit.bean.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class LocalFileDatabase {
    
    private static final String DB_FOLDER = "database";
    private static final String USERS_FILE = DB_FOLDER + "/users.txt";
    private static final String GYMS_FILE = DB_FOLDER + "/gyms.txt";
    private static final String SLOTS_FILE = DB_FOLDER + "/slots.txt";
    private static final String BOOKINGS_FILE = DB_FOLDER + "/bookings.txt";
    private static final String COUNTERS_FILE = DB_FOLDER + "/counters.txt";

    private static AtomicInteger userIdCounter = new AtomicInteger(1000);
    private static AtomicInteger gymIdCounter = new AtomicInteger(2000);
    private static AtomicInteger slotIdCounter = new AtomicInteger(3000);
    private static AtomicInteger bookingIdCounter = new AtomicInteger(4000);
    
    static {
        initializeDatabase();
        loadCounters();
    }
    
    /**
     * Initialize database folder and files
     */
    private static void initializeDatabase() {
        File dbFolder = new File(DB_FOLDER);
        if (!dbFolder.exists()) {
            dbFolder.mkdirs();
        }
        
        createFileIfNotExists(USERS_FILE);
        createFileIfNotExists(GYMS_FILE);
        createFileIfNotExists(SLOTS_FILE);
        createFileIfNotExists(BOOKINGS_FILE);
        createFileIfNotExists(COUNTERS_FILE);
    }
    
    private static void createFileIfNotExists(String filepath) {
        File file = new File(filepath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Error creating file: " + filepath);
            }
        }
    }
    
    /**
     * Load ID counters from file
     */
    private static void loadCounters() {
        try (BufferedReader reader = new BufferedReader(new FileReader(COUNTERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    switch (parts[0]) {
                        case "USER":
                            userIdCounter.set(Integer.parseInt(parts[1]));
                            break;
                        case "GYM":
                            gymIdCounter.set(Integer.parseInt(parts[1]));
                            break;
                        case "SLOT":
                            slotIdCounter.set(Integer.parseInt(parts[1]));
                            break;
                        case "BOOKING":
                            bookingIdCounter.set(Integer.parseInt(parts[1]));
                            break;
                    }
                }
            }
        } catch (IOException e) {
        }
    }
    
    /**
     * Save ID counters to file
     */
    private static void saveCounters() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(COUNTERS_FILE))) {
            writer.println("USER=" + userIdCounter.get());
            writer.println("GYM=" + gymIdCounter.get());
            writer.println("SLOT=" + slotIdCounter.get());
            writer.println("BOOKING=" + bookingIdCounter.get());
        } catch (IOException e) {
            System.err.println("Error saving counters");
        }
    }
    
    // ==================== USER OPERATIONS ====================
    
    /**
     * Generate new user ID
     */
    public static String generateUserId() {
        String id = "USR" + userIdCounter.getAndIncrement();
        saveCounters();
        return id;
    }
    
    /**
     * Save user to file
     */
    public static void saveUser(User user) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE, true))) {
            writer.println(userToString(user));
        } catch (IOException e) {
            System.err.println("Error saving user: " + e.getMessage());
        }
    }
    
    /**
     * Load all users from file
     */
    public static Map<String, User> loadUsers() {
        Map<String, User> users = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    User user = stringToUser(line);
                    if (user != null) {
                        users.put(user.getEmail(), user);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
        return users;
    }
    
    /**
     * Update user in file
     */
    public static void updateUser(User updatedUser) {
        Map<String, User> users = loadUsers();
        users.put(updatedUser.getEmail(), updatedUser);
        saveAllUsers(users);
    }
    
    private static void saveAllUsers(Map<String, User> users) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE))) {
            for (User user : users.values()) {
                writer.println(userToString(user));
            }
        } catch (IOException e) {
            System.err.println("Error saving all users: " + e.getMessage());
        }
    }
    
    private static String userToString(User user) {
        return String.join("|",
            user.getUserID() != null ? user.getUserID() : "",
            user.getName() != null ? user.getName() : "",
            user.getEmail() != null ? user.getEmail() : "",
            user.getPhoneNumber() != null ? user.getPhoneNumber() : "",
            user.getCity() != null ? user.getCity() : "",
            user.getPassword() != null ? user.getPassword() : "",
            user.getRole() != null && user.getRole().getRoleName() != null ? user.getRole().getRoleName() : "CUSTOMER",
            String.valueOf(user.isActive())
        );
    }
    
    private static User stringToUser(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length >= 8) {
                User user = new User();
                user.setUserID(parts[0]);
                user.setName(parts[1]);
                user.setEmail(parts[2]);
                user.setPhoneNumber(parts[3]);
                user.setCity(parts[4]);
                user.setPassword(parts[5]);
                
                Role role = new Role();
                role.setRoleName(parts[6]);
                user.setRole(role);
                
                user.setActive(Boolean.parseBoolean(parts[7]));
                return user;
            }
        } catch (Exception e) {
            System.err.println("Error parsing user: " + e.getMessage());
        }
        return null;
    }
    
    // ==================== GYM OPERATIONS ====================
    
    /**
     * Generate new gym ID
     */
    public static String generateGymId() {
        String id = "GYM" + gymIdCounter.getAndIncrement();
        saveCounters();
        return id;
    }
    
    /**
     * Save gym to file
     */
    public static void saveGym(Gym gym) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(GYMS_FILE, true))) {
            writer.println(gymToString(gym));
        } catch (IOException e) {
            System.err.println("Error saving gym: " + e.getMessage());
        }
    }
    
    /**
     * Load all gyms from file
     */
    public static List<Gym> loadGyms() {
        List<Gym> gyms = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(GYMS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Gym gym = stringToGym(line);
                    if (gym != null) {
                        gyms.add(gym);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading gyms: " + e.getMessage());
        }
        return gyms;
    }
    
    /**
     * Update gym in file
     */
    public static void updateGym(Gym updatedGym) {
        List<Gym> gyms = loadGyms();
        for (int i = 0; i < gyms.size(); i++) {
            if (gyms.get(i).getGymId().equals(updatedGym.getGymId())) {
                gyms.set(i, updatedGym);
                break;
            }
        }
        saveAllGyms(gyms);
    }
    
    private static void saveAllGyms(List<Gym> gyms) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(GYMS_FILE))) {
            for (Gym gym : gyms) {
                writer.println(gymToString(gym));
            }
        } catch (IOException e) {
            System.err.println("Error saving all gyms: " + e.getMessage());
        }
    }
    
    private static String gymToString(Gym gym) {
        return String.join("|",
            gym.getGymId() != null ? gym.getGymId() : "",
            gym.getGymName() != null ? gym.getGymName() : "",
            gym.getLocation() != null ? gym.getLocation() : "",
            gym.getGymOwnerId() != null ? gym.getGymOwnerId() : "",
            String.valueOf(gym.isApproved())
        );
    }
    
    private static Gym stringToGym(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length >= 5) {
                Gym gym = new Gym();
                gym.setGymId(parts[0]);
                gym.setGymName(parts[1]);
                gym.setLocation(parts[2]);
                gym.setGymOwnerId(parts[3]);
                gym.setApproved(Boolean.parseBoolean(parts[4]));
                return gym;
            }
        } catch (Exception e) {
            System.err.println("Error parsing gym: " + e.getMessage());
        }
        return null;
    }
    
    // ==================== SLOT OPERATIONS ====================
    
    /**
     * Generate new slot ID
     */
    public static String generateSlotId() {
        String id = "SLT" + slotIdCounter.getAndIncrement();
        saveCounters();
        return id;
    }
    
    /**
     * Save slot to file
     */
    public static void saveSlot(Slot slot) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SLOTS_FILE, true))) {
            writer.println(slotToString(slot));
        } catch (IOException e) {
            System.err.println("Error saving slot: " + e.getMessage());
        }
    }
    
    /**
     * Load all slots from file
     */
    public static List<Slot> loadSlots() {
        List<Slot> slots = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(SLOTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Slot slot = stringToSlot(line);
                    if (slot != null) {
                        slots.add(slot);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading slots: " + e.getMessage());
        }
        return slots;
    }
    
    /**
     * Update slot in file
     */
    public static void updateSlot(Slot updatedSlot) {
        List<Slot> slots = loadSlots();
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).getSlotId().equals(updatedSlot.getSlotId())) {
                slots.set(i, updatedSlot);
                break;
            }
        }
        saveAllSlots(slots);
    }
    
    private static void saveAllSlots(List<Slot> slots) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SLOTS_FILE))) {
            for (Slot slot : slots) {
                writer.println(slotToString(slot));
            }
        } catch (IOException e) {
            System.err.println("Error saving all slots: " + e.getMessage());
        }
    }
    
    private static String slotToString(Slot slot) {
        return String.join("|",
            slot.getSlotId() != null ? slot.getSlotId() : "",
            slot.getGymId() != null ? slot.getGymId() : "",
            slot.getStartTime() != null ? slot.getStartTime().toString() : "",
            slot.getEndTime() != null ? slot.getEndTime().toString() : "",
            String.valueOf(slot.getCapacity()),
            String.valueOf(slot.getAvailableSeats())
        );
    }
    
    private static Slot stringToSlot(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length >= 6) {
                Slot slot = new Slot();
                slot.setSlotId(parts[0]);
                slot.setGymId(parts[1]);
                slot.setStartTime(LocalTime.parse(parts[2]));
                slot.setEndTime(LocalTime.parse(parts[3]));
                slot.setCapacity(Integer.parseInt(parts[4]));
                slot.setAvailableSeats(Integer.parseInt(parts[5]));
                return slot;
            }
        } catch (Exception e) {
            System.err.println("Error parsing slot: " + e.getMessage());
        }
        return null;
    }
    
    // ==================== BOOKING OPERATIONS ====================
    
    /**
     * Generate new booking ID
     */
    public static String generateBookingId() {
        String id = "BKG" + bookingIdCounter.getAndIncrement();
        saveCounters();
        return id;
    }
    
    /**
     * Save booking to file
     */
    public static void saveBooking(Booking booking) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BOOKINGS_FILE, true))) {
            writer.println(bookingToString(booking));
        } catch (IOException e) {
            System.err.println("Error saving booking: " + e.getMessage());
        }
    }
    
    /**
     * Load all bookings from file
     */
    public static List<Booking> loadBookings() {
        List<Booking> bookings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKINGS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Booking booking = stringToBooking(line);
                    if (booking != null) {
                        bookings.add(booking);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading bookings: " + e.getMessage());
        }
        return bookings;
    }
    
    /**
     * Update booking in file
     */
    public static void updateBooking(Booking updatedBooking) {
        List<Booking> bookings = loadBookings();
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getBookingId().equals(updatedBooking.getBookingId())) {
                bookings.set(i, updatedBooking);
                break;
            }
        }
        saveAllBookings(bookings);
    }
    
    /**
     * Delete booking from file
     */
    public static void deleteBooking(String bookingId) {
        List<Booking> bookings = loadBookings();
        bookings.removeIf(b -> b.getBookingId().equals(bookingId));
        saveAllBookings(bookings);
    }
    
    private static void saveAllBookings(List<Booking> bookings) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BOOKINGS_FILE))) {
            for (Booking booking : bookings) {
                writer.println(bookingToString(booking));
            }
        } catch (IOException e) {
            System.err.println("Error saving all bookings: " + e.getMessage());
        }
    }
    
    private static String bookingToString(Booking booking) {
        return String.join("|",
            booking.getBookingId() != null ? booking.getBookingId() : "",
            booking.getUserId() != null ? booking.getUserId() : "",
            booking.getSlotId() != null ? booking.getSlotId() : "",
            booking.getGymId() != null ? booking.getGymId() : "",
            booking.getBookingDate() != null ? booking.getBookingDate().toString() : "",
            booking.getStatus() != null ? booking.getStatus() : "",
            booking.getCreatedAt() != null ? booking.getCreatedAt().toString() : ""
        );
    }
    
    private static Booking stringToBooking(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length >= 7) {
                Booking booking = new Booking();
                booking.setBookingId(parts[0]);
                booking.setUserId(parts[1]);
                booking.setSlotId(parts[2]);
                booking.setGymId(parts[3]);
                booking.setBookingDate(LocalDate.parse(parts[4]));
                booking.setStatus(parts[5]);
                booking.setCreatedAt(LocalDateTime.parse(parts[6]));
                return booking;
            }
        } catch (Exception e) {
            System.err.println("Error parsing booking: " + e.getMessage());
        }
        return null;
    }
}
