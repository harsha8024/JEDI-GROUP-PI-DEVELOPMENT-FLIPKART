package com.flipfit.business;

import com.flipfit.bean.*;
import com.flipfit.dao.CustomerDAO;
import com.flipfit.dao.GymOwnerDAO;
import com.flipfit.exception.InvalidCredentialsException;
import com.flipfit.exception.UserNotFoundException;
import com.flipfit.dao.AdminDAO;
import java.util.Map;
import java.util.HashMap;

/**
 * The Class GymUserServiceImpl.
 *
 * @author team pi
 * @ClassName "GymUserServiceImpl"
 */
public class GymUserServiceImpl implements GymUserInterface {

    /** The customer DAO. */
    private CustomerDAO customerDAO;

    /** The gym owner DAO. */
    private GymOwnerDAO gymOwnerDAO;

    /** The admin DAO. */
    private AdminDAO adminDAO;

    /** Service to handle the approval requests */
    private RegistrationInterface registrationService = new RegistrationServiceImpl();

    /**
     * Instantiates a new gym user service impl.
     */
    public GymUserServiceImpl() {
        this.customerDAO = new CustomerDAO();
        this.gymOwnerDAO = new GymOwnerDAO();
        this.adminDAO = new AdminDAO();
    }

    /**
     * Gets the user map.
     *
     * @return the user map
     */
    public static Map<String, User> getUserMap() {
        Map<String, User> allUsers = new HashMap<>();
        
        CustomerDAO customerDAO = new CustomerDAO();
        allUsers.putAll(customerDAO.getAllCustomers());
        
        GymOwnerDAO ownerDAO = new GymOwnerDAO();
        allUsers.putAll(ownerDAO.getAllGymOwners());
        
        AdminDAO adminDAO = new AdminDAO();
        allUsers.putAll(adminDAO.getAllAdmins());
        
        return allUsers;
    }

    /**
     * Register a new user.
     *
     * @param user the user
     */
    @Override
    public void register(User user) {
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
        
        // Account is INACTIVE until Admin approves
        customer.setActive(false);
        
        Role role = new Role();
        role.setRoleName("CUSTOMER");
        customer.setRole(role);
        
        if (customerDAO.saveCustomer(customer)) {
            System.out.println("Customer data saved. Creating registration request...");
            registrationService.createRegistration(customerId, "CUSTOMER");
            System.out.println("Registration successful! Your account is pending Admin approval.");
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
        
        owner.setActive(false);
        
        if (user instanceof GymOwner) {
            owner.setPanNumber(((GymOwner) user).getPanNumber());
            owner.setAadharNumber(((GymOwner) user).getAadharNumber());
        }
        
        Role role = new Role();
        role.setRoleName("GYM_OWNER");
        owner.setRole(role);
        
        if (gymOwnerDAO.saveGymOwner(owner)) {
            System.out.println("Gym Owner data saved. Creating registration request...");
            registrationService.createRegistration(ownerId, "GYM_OWNER");
            System.out.println("Registration successful! Your account is pending Admin approval.");
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
        admin.setActive(true); 
        
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
    public boolean login(String email, String password) throws UserNotFoundException, InvalidCredentialsException {
        GymCustomer customer = customerDAO.getCustomerByEmail(email);
        if (customer != null) {
            if (!customer.getPassword().equals(password)) throw new InvalidCredentialsException("Incorrect password.");
            if (!customer.isActive()) throw new InvalidCredentialsException("Account is inactive. Please wait for Admin approval.");
            return true;
        }

        GymOwner owner = gymOwnerDAO.getGymOwnerByEmail(email);
        if (owner != null) {
            if (!owner.getPassword().equals(password)) throw new InvalidCredentialsException("Incorrect password.");
            if (!owner.isActive()) throw new InvalidCredentialsException("Owner account pending approval.");
            return true;
        }

        GymAdmin admin = adminDAO.getAdminByEmail(email);
        if (admin != null) {
            if (!admin.getPassword().equals(password)) throw new InvalidCredentialsException("Incorrect password.");
            return true;
        }

        throw new UserNotFoundException("No user found with email: " + email);
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        GymCustomer customer = customerDAO.getCustomerByEmail(email);
        if (customer != null) {
            customer.setPassword(newPassword);
            if (customerDAO.updateCustomer(customer)) {
                System.out.println("Password updated successfully!");
                return;
            }
        }
        
        GymOwner owner = gymOwnerDAO.getGymOwnerByEmail(email);
        if (owner != null) {
            owner.setPassword(newPassword);
            if (gymOwnerDAO.updateGymOwner(owner)) {
                System.out.println("Password updated successfully!");
                return;
            }
        }
        
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