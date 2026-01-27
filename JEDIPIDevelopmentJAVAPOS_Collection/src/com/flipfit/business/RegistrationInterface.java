package com.flipfit.business;

import com.flipfit.bean.Registration;
import java.util.List;

public interface RegistrationInterface {
    /**
     * Creates a new registration request for a user
     */
    Registration createRegistration(String userId, String role);

    /**
     * Approves a pending registration
     */
    boolean approveRegistration(String registrationId, String adminId);

    /**
     * Rejects a pending registration
     */
    boolean rejectRegistration(String registrationId, String adminId);

    /**
     * Gets all pending registrations (for Admin view)
     */
    List<Registration> getPendingRegistrations();
}