package com.flipfit.business;

import com.flipfit.bean.*;
import com.flipfit.dao.CustomerDAO;
import com.flipfit.dao.GymOwnerDAO;
import com.flipfit.dao.AdminDAO;
import java.util.Map;
import java.util.HashMap;

public class GymUserServiceImpl implements GymUserInterface {

    private CustomerDAO customerDAO;
    private GymOwnerDAO gymOwnerDAO;
    private AdminDAO adminDAO;

    public GymUserServiceImpl() {
        this.customerDAO = new CustomerDAO();
        this.gymOwnerDAO = new GymOwnerDAO();
        this.adminDAO = new AdminDAO();
    }

    // Static getter for Admin service access - returns combined user map
    public static Map<String, User> getUserMap() {
        Map<String, User> allUsers = new HashMap<>();
        
        // Get all customers
        CustomerDAO customerDAO = new CustomerDAO();
        allUsers.putAll(customerDAO.getAllCustomers());
        
        // Get all gym owners
        GymOwnerDAO ownerDAO = new GymOwnerDAO();
        allUsers.putAll(ownerDAO.getAllGymOwners());
        
        // Get all admins
        AdminDAO adminDAO = new AdminDAO();
        allUsers.putAll(adminDAO.getAllAdmins());
        
        return allUsers;
    }

    @Override
    public void register(User user) {
        // Set user as active by default
        user.setActive(true);
        
        // Determine role and use appropriate DAO
        String roleName = user.getRole() != null ? user.getRole().getRoleName() : "CUSTOMER";
        
        switch (roleName.toUpperCase()) {
            case "CUSTOMER":
                registerCustomer(user);
                break;
            case "OWNER":
            case "GYM_OWNER":
                registerGymOwner(user);
                break;
            case "ADMIN":
                registerAdmin(user);
                break;
            default:
                System.err.println("Invalid role: " + roleName);
        }
    }
    
    private void registerCustomer(User user) {
        String customerId = customerDAO.generateCustomerId();
        user.setUserID(customerId);
        
        GymCustomer customer = new GymCustomer();
        customer.setUserID(customerId);
        customer.setName(user.getName());
        customer.setEmail(user.getEmail());
        customer.setPhoneNumber(user.getPhoneNumber());
        customer.setCity(user.getCity());
        customer.setPassword(user.getPassword());
        customer.setActive(true);
        
        Role role = new Role();
        role.setRoleName("CUSTOMER");
        customer.setRole(role);
        
        if (customerDAO.saveCustomer(customer)) {
            System.out.println("Customer registration successful: " + customer.getName() + " (ID: " + customerId + ")");
        } else {
            System.err.println("Customer registration failed for: " + customer.getName());
        }
    }
    
    private void registerGymOwner(User user) {
        String ownerId = gymOwnerDAO.generateOwnerId();
        user.setUserID(ownerId);
        
        GymOwner owner = new GymOwner();
        owner.setUserID(ownerId);
        owner.setName(user.getName());
        owner.setEmail(user.getEmail());
        owner.setPhoneNumber(user.getPhoneNumber());
        owner.setCity(user.getCity());
        owner.setPassword(user.getPassword());
        owner.setActive(false); // Gym owners need admin approval
        
        // Set PAN and Aadhar if available
        if (user instanceof GymOwner) {
            owner.setPanNumber(((GymOwner) user).getPanNumber());
            owner.setAadharNumber(((GymOwner) user).getAadharNumber());
        }
        
        Role role = new Role();
        role.setRoleName("OWNER");
        owner.setRole(role);
        
        if (gymOwnerDAO.saveGymOwner(owner)) {
            System.out.println("Gym Owner registration successful: " + owner.getName() + " (ID: " + ownerId + ")");
            System.out.println("Note: Account is pending admin approval.");
        } else {
            System.err.println("Gym Owner registration failed for: " + owner.getName());
        }
    }
    
    private void registerAdmin(User user) {
        String adminId = adminDAO.generateAdminId();
        user.setUserID(adminId);
        
        GymAdmin admin = new GymAdmin();
        admin.setUserID(adminId);
        admin.setName(user.getName());
        admin.setEmail(user.getEmail());
        admin.setPhoneNumber(user.getPhoneNumber());
        admin.setCity(user.getCity());
        admin.setPassword(user.getPassword());
        admin.setActive(true); // Admins are always active
        
        Role role = new Role();
        role.setRoleName("ADMIN");
        admin.setRole(role);
        
        if (adminDAO.saveAdmin(admin)) {
            System.out.println("Admin registration successful: " + admin.getName() + " (ID: " + adminId + ")");
        } else {
            System.err.println("Admin registration failed for: " + admin.getName());
        }
    }

    @Override
    public boolean login(String email, String password) {
        // Try to find user in customers first
        GymCustomer customer = customerDAO.getCustomerByEmail(email);
        if (customer != null && customer.getPassword().equals(password)) {
            if (customer.isActive()) {
                System.out.println("Login successful: " + customer.getName() + " (Customer)");
                return true;
            } else {
                System.out.println("Account is inactive. Please contact admin.");
                return false;
            }
        }
        
        // Try gym owners
        GymOwner owner = gymOwnerDAO.getGymOwnerByEmail(email);
        if (owner != null && owner.getPassword().equals(password)) {
            if (owner.isActive()) {
                System.out.println("Login successful: " + owner.getName() + " (Gym Owner)");
                return true;
            } else {
                System.out.println("Account is pending approval or inactive. Please contact admin.");
                return false;
            }
        }
        
        // Try admins
        GymAdmin admin = adminDAO.getAdminByEmail(email);
        if (admin != null && admin.getPassword().equals(password)) {
            System.out.println("Login successful: " + admin.getName() + " (Admin)");
            return true;
        }
        
        System.out.println("Invalid email or password.");
        return false;
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        // Try to find and update in customers
        GymCustomer customer = customerDAO.getCustomerByEmail(email);
        if (customer != null) {
            customer.setPassword(newPassword);
            if (customerDAO.updateCustomer(customer)) {
                System.out.println("Password updated successfully!");
                return;
            }
        }
        
        // Try gym owners
        GymOwner owner = gymOwnerDAO.getGymOwnerByEmail(email);
        if (owner != null) {
            owner.setPassword(newPassword);
            if (gymOwnerDAO.updateGymOwner(owner)) {
                System.out.println("Password updated successfully!");
                return;
            }
        }
        
        // Try admins
        GymAdmin admin = adminDAO.getAdminByEmail(email);
        if (admin != null) {
            admin.setPassword(newPassword);
            if (adminDAO.updateAdmin(admin)) {
                System.out.println("Password updated successfully!");
                return;
            }
        }
        
        System.out.println("User not found!");
    }

    @Override
    public void logout() {
        System.out.println("User logged out successfully.");
    }

}