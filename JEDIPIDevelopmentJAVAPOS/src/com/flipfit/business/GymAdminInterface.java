package com.flipfit.business;

public interface GymAdminInterface {
    void approveGym(String gymId);
    void approveCustomer(String customerId);
    void manageOwners(String ownerId);
}