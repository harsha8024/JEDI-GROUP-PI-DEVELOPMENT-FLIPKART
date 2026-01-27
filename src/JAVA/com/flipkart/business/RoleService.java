package com.flipkart.business;

import constant.Role;

public interface RoleService {
    boolean validateAccess(Role userRole, Role requiredRole);
}
