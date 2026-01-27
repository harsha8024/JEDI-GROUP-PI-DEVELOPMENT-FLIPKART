// TODO: Auto-generated Javadoc
package com.flipfit.business;

import com.flipfit.bean.User;
import com.flipfit.exception.InvalidCredentialsException;
import com.flipfit.exception.RegistrationFailedException;
import com.flipfit.exception.UserNotFoundException;

/**
 * The Interface GymUserInterface.
 *
 * @author team pi
 * @ClassName "GymUserInterface"
 */
public interface GymUserInterface {

    /**
     * Register.
     *
     * @param user the user
     * @throws RegistrationFailedException the registration failed exception
     */
    void register(User user) throws RegistrationFailedException;

    /**
     * Login.
     *
     * @param email    the email
     * @param password the password
     * @return true, if successful
     * @throws UserNotFoundException       the user not found exception
     * @throws InvalidCredentialsException the invalid credentials exception
     */
    boolean login(String email, String password) throws UserNotFoundException, InvalidCredentialsException;

    /**
     * Update password.
     *
     * @param email       the email
     * @param newPassword the new password
     */
    void updatePassword(String email, String newPassword);

    /**
     * Logout.
     */
    void logout();
}