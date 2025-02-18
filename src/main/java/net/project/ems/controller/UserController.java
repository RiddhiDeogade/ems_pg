package net.project.ems.controller;

import net.project.ems.entity.User;
import net.project.ems.dto.UserDto;
import net.project.ems.service.JwtService;
import net.project.ems.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;  // Inject JwtService

    // Register a user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        try {
            User registeredUser = userService.registerUser(userDto.getEmail(), userDto.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Log in a user
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDto userDto) {
        try {
            Optional<User> loggedInUser = Optional.ofNullable(userService.loginUser(userDto.getEmail(), userDto.getPassword()));
            if (loggedInUser.isPresent()) {
                // Generate JWT Token
                String token = jwtService.generateToken(loggedInUser.get());
                // Prepare the response with userId and token
                return ResponseEntity.ok(new LoginResponse(loggedInUser.get().getId(), token));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + id);
    }

    // DTO for login response containing userId and token
    public static class LoginResponse {
        private Long userId;
        private String token;

        public LoginResponse(Long userId, String token) {
            this.userId = userId;
            this.token = token;
        }

        public Long getUserId() {
            return userId;
        }

        public String getToken() {
            return token;
        }
    }
}
