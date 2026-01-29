// TODO: Auto-generated Javadoc
package com.flipfit.business;

import com.flipfit.bean.Registration;
import com.flipfit.dao.AdminDAO;
import com.flipfit.dao.RegistrationDAO;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.flipfit.exception.InvalidInputException;
import com.flipfit.exception.RegistrationFailedException;
import com.flipfit.exception.RegistrationAlreadyExistsException;

/**
 * The Class RegistrationServiceImpl.
 * Implementation of RegistrationInterface that provides registration management functionality.
 *
 * @author team pi
 * @ClassName "RegistrationServiceImpl"
 */
public class RegistrationServiceImpl implements RegistrationInterface {

    /** The registration DAO. */
    private RegistrationDAO registrationDAO = new RegistrationDAO();
    
    /** The admin DAO. */
    private AdminDAO adminDAO = new AdminDAO();

    /**
     * Creates the registration.
     * Creates a new registration request for a user with specified role.
     *
     * @param userId the user id
     * @param role the role
     * @return the registration
     */
    @Override
    public Registration createRegistration(String userId, String role) throws InvalidInputException, RegistrationFailedException, RegistrationAlreadyExistsException {
        if (userId == null || userId.isBlank() || role == null || role.isBlank()) {
            throw new InvalidInputException("User ID and role must be provided for registration.");
        }

        // Prevent duplicate registration requests
        List<Registration> pending = registrationDAO.getPendingRegistrations();
        boolean exists = pending.stream().anyMatch(r -> r.getUserId().equals(userId));
        if (exists) {
            throw new RegistrationAlreadyExistsException("A pending registration already exists for user: " + userId);
        }

        Registration registration = new Registration();
        registration.setRegistrationId(UUID.randomUUID().toString());
        registration.setUserId(userId);
        registration.setRoleId(role); // Stores "CUSTOMER" or "GYM_OWNER"
        registration.setRegistrationDate(LocalDateTime.now());
        registration.setStatus("PENDING");
        
        if (registrationDAO.saveRegistration(registration)) {
            System.out.println("Registration request created for User: " + userId);
            return registration;
        } else {
            throw new RegistrationFailedException("Failed to save registration request for user: " + userId);
        }
    }

    /**
     * Approve registration.
     * Approves a pending registration request and activates the user account.
     *
     * @param registrationId the registration id
     * @param adminId the admin id
     * @return true, if successful
     */
    @Override
    public boolean approveRegistration(String registrationId, String adminId) throws InvalidInputException, RegistrationFailedException {
        if (registrationId == null || registrationId.isBlank() || adminId == null || adminId.isBlank()) {
            throw new InvalidInputException("Registration ID and admin ID must be provided.");
        }

        // 1. Fetch the registration to find out who the user is and what role they have
        Registration reg = registrationDAO.getRegistrationById(registrationId);
        
        if (reg == null) {
            throw new RegistrationFailedException("Registration request not found.");
        }

        if (!"PENDING".equalsIgnoreCase(reg.getStatus())) {
            throw new RegistrationFailedException("Registration is already " + reg.getStatus());
        }

        // 2. Activate the User in the correct table using AdminDAO
        boolean userActivated = false;
        String role = reg.getRoleId().toUpperCase();

        switch (role) {
            case "CUSTOMER":
                userActivated = adminDAO.approveCustomer(reg.getUserId());
                break;
            case "GYM_OWNER":
            case "OWNER":
                userActivated = adminDAO.approveGymOwner(reg.getUserId());
                break;
            default:
                throw new RegistrationFailedException("Unknown role type " + role);
        }

        // 3. If User was activated, update the Registration Request status
        if (userActivated) {
            boolean statusUpdated = registrationDAO.updateRegistrationStatus(registrationId, "APPROVED", adminId);
            if (statusUpdated) {
                System.out.println("Registration " + registrationId + " approved. User " + reg.getUserId() + " is now active.");
                return true;
            }
        }
        
        throw new RegistrationFailedException("Failed to complete approval process.");
    }

    /**
     * Reject registration.
     * Rejects a pending registration request.
     *
     * @param registrationId the registration id
     * @param adminId the admin id
     * @return true, if successful
     */
    @Override
    public boolean rejectRegistration(String registrationId, String adminId) throws InvalidInputException, RegistrationFailedException {
        if (registrationId == null || registrationId.isBlank() || adminId == null || adminId.isBlank()) {
            throw new InvalidInputException("Registration ID and admin ID must be provided.");
        }
        // Simple update to "REJECTED" - no need to touch the User table
        boolean statusUpdated = registrationDAO.updateRegistrationStatus(registrationId, "REJECTED", adminId);
        
        if (statusUpdated) {
            System.out.println("Registration " + registrationId + " has been rejected.");
            return true;
        }
        throw new RegistrationFailedException("Failed to reject registration: " + registrationId);
    }

    /**
     * Gets the pending registrations.
     * Retrieves all registration requests with pending status.
     *
     * @return the pending registrations
     */
    @Override
    public List<Registration> getPendingRegistrations() {
        List<Registration> pendingList = registrationDAO.getPendingRegistrations();
        if (pendingList.isEmpty()) {
            return Collections.emptyList();
        }
        return pendingList;
    }
}