package com.example.loginapi.controller;

import com.example.loginapi.dto.LoginRequest;
import com.example.loginapi.dto.LoginResponse;
import com.example.loginapi.dto.RegisterRequest;
import com.example.loginapi.entity.User;
import com.example.loginapi.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();

        if (username == null || username.isBlank()) {
            return ResponseEntity.ok(new LoginResponse(false, "Username is required."));
        }
        if (password == null || password.isBlank()) {
            return ResponseEntity.ok(new LoginResponse(false, "Password is required."));
        }
        if (password.length() < 4) {
            return ResponseEntity.ok(new LoginResponse(false, "Password must be at least 4 characters."));
        }
        if (email == null || email.isBlank()) {
            return ResponseEntity.ok(new LoginResponse(false, "Email is required."));
        }
        if (!email.contains("@")) {
            return ResponseEntity.ok(new LoginResponse(false, "Enter a valid email."));
        }

        if (userRepository.existsByUsername(username.trim())) {
            return ResponseEntity.ok(new LoginResponse(false, "Username already taken."));
        }
        if (userRepository.existsByEmail(email.trim())) {
            return ResponseEntity.ok(new LoginResponse(false, "Email already registered."));
        }

        User user = new User(username.trim(), password, email.trim());
        userRepository.save(user);

        return ResponseEntity.ok(
            new LoginResponse(true, "Account created! You can now sign in.", "token-" + user.getId())
        );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return ResponseEntity.ok(
                new LoginResponse(false, "Username and password are required.")
            );
        }

        var optUser = userRepository.findByUsername(username.trim());
        if (optUser.isEmpty()) {
            return ResponseEntity.ok(
                new LoginResponse(false, "Invalid username or password.")
            );
        }

        User user = optUser.get();
        if (!user.getPassword().equals(password)) {
            return ResponseEntity.ok(
                new LoginResponse(false, "Invalid username or password.")
            );
        }

        return ResponseEntity.ok(
            new LoginResponse(true, "Login successful!", "token-" + user.getId())
        );
    }
}
