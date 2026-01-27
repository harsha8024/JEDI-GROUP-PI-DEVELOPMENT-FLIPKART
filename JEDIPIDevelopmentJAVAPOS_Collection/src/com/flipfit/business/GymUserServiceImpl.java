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

<<<<<<< Updated upstream
    /**
     * Instantiates a new gym user service impl.
     */
=======
    // 1. NEW: Add the Registration Service to handle the approval requests
    private RegistrationInterface registrationService = new RegistrationServiceImpl();

>>>>>>> Stashed changes
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
<<<<<<< Updated upstream

        // Get all customers
        CustomerDAO customerDAO = new CustomerDAO();
        allUsers.putAll(customerDAO.getAllCustomers());

        // Get all gym owners
        GymOwnerDAO ownerDAO = new GymOwnerDAO();
        allUsers.putAll(ownerDAO.getAllGymOwners());

        // Get all admins
=======
        
        CustomerDAO customerDAO = new CustomerDAO();
        allUsers.putAll(customerDAO.getAllCustomers());
        
        GymOwnerDAO ownerDAO = new GymOwnerDAO();
        allUsers.putAll(ownerDAO.getAllGymOwners());
        
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
        // Set user as active by default
        user.setActive(true);
=======
        // NOTE: We removed user.setActive(true) from here. 
        // Activation is now handled inside the specific methods below.
>>>>>>> Stashed changes

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
<<<<<<< Updated upstream
        customer.setActive(true);

=======
        
        // 2. CHANGE: Default is now FALSE. User cannot login until Admin approves.
        customer.setActive(false);
        
>>>>>>> Stashed changes
        Role role = new Role();
        role.setRoleName("CUSTOMER");
        customer.setRole(role);

        if (customerDAO.saveCustomer(customer)) {
            System.out.println("Customer data saved. Creating registration request...");
            
            // 3. NEW: Create the 'Paperwork' for Admin to approve
            registrationService.createRegistration(customerId, "CUSTOMER");
            
            System.out.println("Registration successful! Your account is pending Admin approval.");
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
<<<<<<< Updated upstream
        // Make gym owner active immediately so they can login after registration.
        // Gym and slot approvals remain handled by admin separately.
        owner.setActive(true);

=======
        
        // 4. CHANGE: Default is now FALSE.
        owner.setActive(false);
        
>>>>>>> Stashed changes
        // Set PAN and Aadhar if available
        if (user instanceof GymOwner) {
            owner.setPanNumber(((GymOwner) user).getPanNumber());
            owner.setAadharNumber(((GymOwner) user).getAadharNumber());
        }

        Role role = new Role();
        role.setRoleName("GYM_OWNER");
        owner.setRole(role);

        if (gymOwnerDAO.saveGymOwner(owner)) {
            System.out.println("Gym Owner data saved. Creating registration request...");
            
            // 5. NEW: Create the request for Admin
            registrationService.createRegistration(ownerId, "GYM_OWNER");
            
            System.out.println("Registration successful! You cannot log in until an Admin approves your account.");
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
<<<<<<< Updated upstream
        admin.setActive(true); // Admins are always active

=======
        
        // Admins are always active immediately (no self-approval needed)
        admin.setActive(true); 
        
>>>>>>> Stashed changes
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
        GymCustomer customer = customerDAO.getCustomerByEmail(email);
        if (customer != null) {
<<<<<<< Updated upstream
            if (!customer.getPassword().equals(password))
                throw new InvalidCredentialsException("Incorrect password."); //
            if (!customer.isActive())
                throw new InvalidCredentialsException("Account is inactive."); //
=======
            if (!customer.getPassword().equals(password)) throw new InvalidCredentialsException("Incorrect password.");
            
            // This check now works perfectly with the new registration flow!
            if (!customer.isActive()) throw new InvalidCredentialsException("Account is inactive. Please wait for Admin approval.");
            
>>>>>>> Stashed changes
            return true;
        }

        GymOwner owner = gymOwnerDAO.getGymOwnerByEmail(email);
        if (owner != null) {
<<<<<<< Updated upstream
            if (!owner.getPassword().equals(password))
                throw new InvalidCredentialsException("Incorrect password."); //
            if (!owner.isActive())
                throw new InvalidCredentialsException("Owner account pending approval."); //
=======
            if (!owner.getPassword().equals(password)) throw new InvalidCredentialsException("Incorrect password.");
            
            // This check now works perfectly with the new registration flow!
            if (!owner.isActive()) throw new InvalidCredentialsException("Owner account pending approval.");
            
>>>>>>> Stashed changes
            return true;
        }

        GymAdmin admin = adminDAO.getAdminByEmail(email);
        if (admin != null) {
<<<<<<< Updated upstream
            if (!admin.getPassword().equals(password))
                throw new InvalidCredentialsException("Incorrect password."); //
=======
            if (!admin.getPassword().equals(password)) throw new InvalidCredentialsException("Incorrect password.");
>>>>>>> Stashed changes
            return true;
        }

        throw new UserNotFoundException("No user found with email: " + email);
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