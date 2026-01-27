package com.flipfit.business;

import com.flipfit.bean.User;
import com.flipfit.bean.Role;
import com.flipfit.dao.UserDAO;
import java.util.Map;

public class GymUserServiceImpl implements GymUserInterface {

    private UserDAO userDAO;

    public GymUserServiceImpl() {
        this.userDAO = new UserDAO();
    }

    // Static getter for Admin service access - keeping for compatibility but using
    // DAO
    public static Map<String, User> getUserMap() {
        return new UserDAO().getAllUsers();
    }

    @Override
    public void register(User user) {
        // Generate unique user ID using database counter
        String userId = userDAO.generateUserId();
        user.setUserID(userId);

        // Set default role if not set
        if (user.getRole() == null) {
            Role role = new Role();
            role.setRoleName("CUSTOMER");
            role.setDescription("Regular gym customer");
            user.setRole(role);
        }

        // Set user as active
        user.setActive(true);

        // Save to MySQL database
        if (userDAO.saveUser(user)) {
            System.out.println("Registration successful for: " + user.getName() + " (ID: " + userId + ")");
        } else {
            System.err.println("Registration failed for user: " + user.getName());
        }
    }

    @Override
    public boolean login(String email, String password) {
        User user = userDAO.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            user.setActive(true);
            userDAO.updateUser(user);
            return true;
        }
        return false;
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        User user = userDAO.getUserByEmail(email);
        if (user != null) {
            user.setPassword(newPassword);
            if (userDAO.updateUser(user)) {
                System.out.println("Password updated successfully!");
            } else {
                System.err.println("Failed to update password.");
            }
        } else {
            System.out.println("User not found!");
        }
    }

    @Override
    public void logout() {
        System.out.println("User logged out successfully.");
    }

}