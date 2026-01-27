package com.flipfit.business;
import com.flipfit.bean.User;
import com.flipfit.exception.InvalidCredentialsException;
import com.flipfit.exception.RegistrationFailedException;
import com.flipfit.exception.UserNotFoundException;

public interface GymUserInterface {
    void register(User user) throws RegistrationFailedException; 
    boolean login(String email, String password) throws UserNotFoundException, InvalidCredentialsException;
    void updatePassword(String email, String newPassword);
    void logout();
}