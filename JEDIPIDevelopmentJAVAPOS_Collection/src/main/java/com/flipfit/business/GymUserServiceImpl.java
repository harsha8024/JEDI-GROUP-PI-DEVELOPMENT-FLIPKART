package com.flipfit.business;

import com.flipfit.bean.GymAdmin;
import com.flipfit.bean.GymCustomer;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.Role;
import com.flipfit.bean.User;
import com.flipfit.dao.AdminDAO;
import com.flipfit.dao.CustomerDAO;
import com.flipfit.dao.GymOwnerDAO;
import com.flipfit.exception.InvalidCredentialsException;
import com.flipfit.exception.RegistrationFailedException;
import com.flipfit.exception.UserNotFoundException;
import com.flipfit.exception.InvalidInputException;
import com.flipfit.exception.RegistrationAlreadyExistsException;
import java.util.Map;
import java.util.HashMap;
import com.flipfit.utils.ValidationUtils;

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
    public void register(User user) throws RegistrationFailedException, InvalidInputException {
        if (user == null) {
            throw new InvalidInputException("User cannot be null.");
        }
        if (user.getEmail() == null || user.getEmail().isBlank() || !ValidationUtils.isValidEmail(user.getEmail())) {
            throw new InvalidInputException("Invalid or missing email address.");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new InvalidInputException("Password must be provided.");
        }
        if (user.getPhoneNumber() == null || !ValidationUtils.isValidPhone(user.getPhoneNumber())) {
            throw new InvalidInputException("Phone number must be 10 digits.");
        }

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
                throw new com.flipfit.exception.RegistrationFailedException("Invalid role: " + roleName);
        }
    }
    
    private void registerCustomer(User user) throws RegistrationFailedException, InvalidInputException {
        String customerId = customerDAO.generateCustomerId();
        user.setUserID(customerId);

        GymCustomer customer = new GymCustomer();
        customer.setUserID(customerId);
        customer.setName(user.getName());
        customer.setEmail(user.getEmail());
        customer.setPhoneNumber(user.getPhoneNumber());
        customer.setCity(user.getCity());
        customer.setPassword(user.getPassword());

        // Customer account is ACTIVE immediately (no approval needed for user account)
        customer.setActive(true);

        Role role = new Role();
        role.setRoleName("CUSTOMER");
        customer.setRole(role);

        if (customerDAO.saveCustomer(customer)) {
            System.out.println("Customer registration successful! You can now log in immediately.");
        } else {
            throw new RegistrationFailedException("Customer registration failed for: " + customer.getName());
        }
    }

    private void registerGymOwner(User user) throws RegistrationFailedException, InvalidInputException {
        String ownerId = gymOwnerDAO.generateOwnerId();
        user.setUserID(ownerId);

        GymOwner owner = new GymOwner();
        owner.setUserID(ownerId);
        owner.setName(user.getName());
        owner.setEmail(user.getEmail());
        owner.setPhoneNumber(user.getPhoneNumber());
        owner.setCity(user.getCity());
        owner.setPassword(user.getPassword());

        // Gym owner is ACTIVE immediately after registration (no approval needed for user account)
        // Only gyms and slots require admin approval
        owner.setActive(true);

        if (user instanceof GymOwner) {
            String pan = ((GymOwner) user).getPanNumber();
            String aadhar = ((GymOwner) user).getAadharNumber();
            String gstin = ((GymOwner) user).getGstinNumber();

            if (pan != null && !pan.isBlank()) {
                if (!ValidationUtils.isValidPan(pan)) {
                    throw new InvalidInputException("Invalid PAN number. Expected format: 5 letters, 4 digits, 1 letter.");
                }
                owner.setPanNumber(pan);
            }
            if (aadhar != null && !aadhar.isBlank()) {
                if (!ValidationUtils.isValidAadhar(aadhar)) {
                    throw new InvalidInputException("Invalid Aadhar number. Expected 12 digits.");
                }
                owner.setAadharNumber(aadhar);
            }
            if (gstin != null && !gstin.isBlank()) {
                if (!ValidationUtils.isValidGstin(gstin)) {
                    throw new InvalidInputException("Invalid GSTIN. Expected 15 characters.");
                }
                owner.setGstinNumber(gstin);
            }
        }

        Role role = new Role();
        role.setRoleName("OWNER");
        owner.setRole(role);

        if (gymOwnerDAO.saveGymOwner(owner)) {
            System.out.println("\n✓ Gym Owner registration successful: " + owner.getName() + " (ID: " + ownerId + ")");
            System.out.println("You can now log in immediately. Your gyms and slots will require admin approval.");
        } else {
            throw new RegistrationFailedException("Gym Owner registration failed for: " + owner.getName());
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
    public boolean login(String email, String password) throws UserNotFoundException, InvalidCredentialsException, InvalidInputException {
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            throw new InvalidInputException("Email and password must be provided.");
        }

        GymCustomer customer = customerDAO.getCustomerByEmail(email);
        if (customer != null) {
            if (!customer.getPassword().equals(password))
                throw new InvalidCredentialsException("Incorrect password.");
            if (!customer.isActive())
                throw new InvalidCredentialsException("Account is inactive. Please wait for Admin approval.");
            return true;
        }

        GymOwner owner = gymOwnerDAO.getGymOwnerByEmail(email);
        if (owner != null) {
            if (!owner.getPassword().equals(password))
                throw new InvalidCredentialsException("Incorrect password.");
            if (!owner.isActive())
                throw new InvalidCredentialsException("Owner account pending approval.");
            return true;
        }

        GymAdmin admin = adminDAO.getAdminByEmail(email);
        if (admin != null) {
            if (!admin.getPassword().equals(password))
                throw new InvalidCredentialsException("Incorrect password.");
            return true;
        }

        throw new UserNotFoundException("No user found with email: " + email);
    }

    @Override
    public void updatePassword(String email, String newPassword) throws InvalidInputException, UserNotFoundException {
        if (email == null || email.isBlank() || newPassword == null || newPassword.isBlank()) {
            throw new InvalidInputException("Email and new password must be provided.");
        }

        GymCustomer customer = customerDAO.getCustomerByEmail(email);
        if (customer != null) {
            customer.setPassword(newPassword);
            if (customerDAO.updateCustomer(customer)) {
                System.out.println("Password updated successfully!");
                return;
            } else {
                throw new UserNotFoundException("Failed to update password for user: " + email);
            }
        }

        GymOwner owner = gymOwnerDAO.getGymOwnerByEmail(email);
        if (owner != null) {
            owner.setPassword(newPassword);
            if (gymOwnerDAO.updateGymOwner(owner)) {
                System.out.println("Password updated successfully!");
                return;
            } else {
                throw new UserNotFoundException("Failed to update password for owner: " + email);
            }
        }

        GymAdmin admin = adminDAO.getAdminByEmail(email);
        if (admin != null) {
            admin.setPassword(newPassword);
            if (adminDAO.updateAdmin(admin)) {
                System.out.println("Password updated successfully!");
                return;
            } else {
                throw new UserNotFoundException("Failed to update password for admin: " + email);
            }
        }
        throw new UserNotFoundException("User not found: " + email);
    }

    @Override
    public void logout() {
        System.out.println("User logged out successfully.");
    }
}