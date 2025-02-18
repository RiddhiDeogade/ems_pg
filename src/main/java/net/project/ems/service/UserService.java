package net.project.ems.service;

import net.project.ems.entity.User;
import net.project.ems.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Hash the password using SHA-256 (You can use BCrypt for better security)
    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while hashing password", e);
        }
    }

    // Register a new user with hashed password
    public User registerUser(String email, String password) {
        // Check if the user already exists
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new IllegalStateException("Email already in use");
        }

        // Hash the password before saving
        String hashedPassword = hashPassword(password);

        // Create a new user
        User newUser = new User(email, hashedPassword);

        // Save the new user in the database
        return userRepository.save(newUser);
    }

    // Log in a user: Check if the hashed password matches
    public User loginUser(String email, String password) {
        // Find user by email
        Optional<User> userOptional = userRepository.findByEmail(email);

        // Check if the user exists
        if (userOptional.isEmpty()) {
            throw new IllegalStateException("User not registered");
        }

        User user = userOptional.get();  // Get the user from the Optional

        // Hash the entered password and compare with stored password
        if (!user.getPassword().equals(hashPassword(password))) {
            throw new IllegalStateException("Invalid credentials");
        }

        return user;
    }

    // Get user by ID
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    // Get user by email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
    }
}
