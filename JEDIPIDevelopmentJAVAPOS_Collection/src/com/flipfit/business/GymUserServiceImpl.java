// TODO: Auto-generated Javadoc
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

    /**
     * Register.
     *
     * @param user the user
     */
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

    /**
     * Register customer.
     *
     * @param user the user
     */
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

    /**
     * Register gym owner.
     *
     * @param user the user
     */
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
        // Make gym owner active immediately so they can login after registration.
        // Gym and slot approvals remain handled by admin separately.
        owner.setActive(true);

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
            System.out.println("You can now log in. Note: Gyms and slots you create may still require admin approval.");
        } else {
            System.err.println("Gym Owner registration failed for: " + owner.getName());
        }
    }

    /**
     * Register admin.
     *
     * @param user the user
     */
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

    /**
     * Login.
     *
     * @param email    the email
     * @param password the password
     * @return true, if successful
     * @throws UserNotFoundException       the user not found exception
     * @throws InvalidCredentialsException the invalid credentials exception
     */
    @Override
    public boolean login(String email, String password) throws UserNotFoundException, InvalidCredentialsException {
        // Checking across all user types
        GymCustomer customer = customerDAO.getCustomerByEmail(email); //
        if (customer != null) {
            if (!customer.getPassword().equals(password))
                throw new InvalidCredentialsException("Incorrect password."); //
            if (!customer.isActive())
                throw new InvalidCredentialsException("Account is inactive."); //
            return true;
        }

        GymOwner owner = gymOwnerDAO.getGymOwnerByEmail(email); //
        if (owner != null) {
            if (!owner.getPassword().equals(password))
                throw new InvalidCredentialsException("Incorrect password."); //
            if (!owner.isActive())
                throw new InvalidCredentialsException("Owner account pending approval."); //
            return true;
        }

        GymAdmin admin = adminDAO.getAdminByEmail(email); //
        if (admin != null) {
            if (!admin.getPassword().equals(password))
                throw new InvalidCredentialsException("Incorrect password."); //
            return true;
        }

        throw new UserNotFoundException("No user found with email: " + email); //
    }

    /**
     * Update password.
     *
     * @param email       the email
     * @param newPassword the new password
     */
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

    /**
     * Logout.
     */
    @Override
    public void logout() {
        System.out.println("User logged out successfully.");
    }

}