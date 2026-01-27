package com.flipkart.business;

import com.flipkart.constant.Role;

public interface RegistrationService {
    void registerUser(String userId, String password, Role role);
}