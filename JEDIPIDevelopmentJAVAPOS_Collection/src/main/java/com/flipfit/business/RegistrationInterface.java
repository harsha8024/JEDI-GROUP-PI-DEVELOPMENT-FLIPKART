package com.flipfit.business;

import com.flipfit.bean.Registration;
import java.util.List;
import com.flipfit.exception.InvalidInputException;
import com.flipfit.exception.RegistrationFailedException;
import com.flipfit.exception.RegistrationAlreadyExistsException;

public interface RegistrationInterface {
    /**
     * Creates a new registration request for a user
     */
    Registration createRegistration(String userId, String role) throws InvalidInputException, RegistrationFailedException, RegistrationAlreadyExistsException;

    /**
     * Approves a pending registration
     */
    boolean approveRegistration(String registrationId, String adminId) throws InvalidInputException, RegistrationFailedException;

    /**
     * Rejects a pending registration
     */
    boolean rejectRegistration(String registrationId, String adminId) throws InvalidInputException, RegistrationFailedException;

    /**
     * Gets all pending registrations (for Admin view)
     */
    List<Registration> getPendingRegistrations();
}