package com.flipkart.business;

import com.flipkart.constant.Role;

public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public void registerUser(String userId, String password, Role role) {
        // This signature now perfectly matches the interface
        System.out.println("User " + userId + " registered successfully as " + role);
    }
}