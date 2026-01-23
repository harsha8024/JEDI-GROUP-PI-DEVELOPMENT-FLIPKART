package com.flipfit.business;

public class GymAdminServiceImpl implements GymAdminInterface {
    @Override
    public void approveGym(String gymId) {
        System.out.println("Gym with ID: " + gymId + " has been approved by Admin.");
    }

    @Override
    public void approveCustomer(String customerId) {
        System.out.println("Customer with ID: " + customerId + " has been approved by Admin.");
    }

    @Override
    public void manageOwners(String ownerId) {
        System.out.println("Managing profile for Owner ID: " + ownerId);
    }
}