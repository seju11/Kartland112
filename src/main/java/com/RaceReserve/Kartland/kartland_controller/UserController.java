package com.RaceReserve.Kartland.kartland_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.RaceReserve.Kartland.kartland_entity.Login;
import com.RaceReserve.Kartland.kartland_entity.LoginRequest;
import com.RaceReserve.Kartland.kartland_entity.User;
import com.RaceReserve.Kartland.kartland_repository.LoginRepository;
import com.RaceReserve.Kartland.kartland_repository.UserRepository;
import com.RaceReserve.Kartland.kartland_service.EmailService;
import com.RaceReserve.Kartland.kartland_service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired private EmailService emailService;
    @Autowired private UserService userService;
    @Autowired private UserRepository userRepository;
    @Autowired private LoginRepository loginRepository;
    @Autowired private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    // Register user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        userService.save(user);

        // Send confirmation to user
        String userSubject = "Welcome to KartlandIndia!";
        String userBody = "Dear " + user.getUsername() + ",\n\nThank you for registering at KartlandIndia.";
        emailService.sendEmail(user.getEmail(), userSubject, userBody);

        // Notify admin
        String ownerEmail = "s3458540@gmail.com";
        String ownerSubject = "New User Registration - KartlandIndia";
        String ownerBody = "A new user has registered:\n\n"
                + "Username: " + user.getUsername() + "\n"
                + "Email: " + user.getEmail() + "\n"
                + "Contact: " + user.getContactNo() + "\n\n"
                + "KartlandIndia System";
        emailService.sendEmail(ownerEmail, ownerSubject, ownerBody);

        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());

        Login loginEntry = new Login();
        loginEntry.setUsername(request.getUsername());
        loginEntry.setLoginTime(LocalDateTime.now());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                loginEntry.setStatus("SUCCESS");
                loginRepository.save(loginEntry);

                try {
                    emailService.sendEmail(user.getEmail(),
                            "Login Notification",
                            "Dear " + request.getUsername() +
                            ",\n\nYou logged into KartlandIndia at " + LocalDateTime.now() + ".");
                } catch (Exception e) {
                    System.err.println("⚠️ Email failed: " + e.getMessage());
                }

                return ResponseEntity.ok("Login successful");
            }
        }

        loginEntry.setStatus("FAILED");
        loginRepository.save(loginEntry);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    // Get all users
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Get user by username
    @GetMapping("/username/{username}")
    public ResponseEntity<Optional<User>> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.getUserByUsername(username);
        return user.isPresent()
                ? ResponseEntity.ok(user)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
