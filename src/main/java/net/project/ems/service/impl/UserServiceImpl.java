package net.project.ems.service.impl;

import net.project.ems.entity.User;
import net.project.ems.repository.UserRepository;
import net.project.ems.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl extends UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(String email, String password) {
        // Check if the user already exists
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("Email already in use");
        }

        // Create a new user
        User newUser = new User(email, password);

        // Save the new user to the database
        return userRepository.save(newUser);
    }

    @Override
    public User loginUser(String email, String password) {
        // Find user by email
        Optional<User> user = userRepository.findByEmail(email);

        // Check if the user exists and if the password matches
        if (user.isEmpty() || !user.get().getPassword().equals(password)) {
            throw new IllegalStateException("Invalid credentials");
        }

        return user.get(); // Return the User object, not a String
    }
}
