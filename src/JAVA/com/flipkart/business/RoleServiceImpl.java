package com.flipkart.business;

import constant.Role;

public class RoleServiceImpl implements RoleService {
    @Override
    public boolean validateAccess(Role userRole, Role requiredRole) {
        // Simple logic: return true if the roles match
        return userRole == requiredRole;
    }
}
