package com.RaceReserve.Kartland.kartland_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LoginRepository loginRepository;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // Save user
        userService.save(user);

        // Send registration confirmation email to the user
        String userSubject = "Welcome to RaceReserve!";
        String userBody = "Dear " + user.getUsername() + ",\n\nThank you for registering at RaceReserve. Your account is now active.";
        emailService.sendEmail(user.getEmail(), userSubject, userBody);

        // Send registration notification email to the owner (you)
        String ownerEmail = "s3458540@gmail.com"; 
        String ownerSubject = "New User Registration - RaceReserve";
        String ownerBody = "A new user has registered on RaceReserve:\n\n"
                + "Username: " + user.getUsername() + "\n"
                + "Email: " + user.getEmail() + "\n"
                + "Contact Number: " + user.getContactNo() + "\n\n"
                + "Thank you,\nRaceReserve Team";
        emailService.sendEmail(ownerEmail, ownerSubject, ownerBody);

        return ResponseEntity.ok().body(Map.of("message", "User registered successfully"));
    }

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
                    String subject = "Login Notification";
                    String body = "Dear " + request.getUsername() + ",\n\nYou have successfully logged into your RaceReserve account at " + LocalDateTime.now() + ".";
                    emailService.sendEmail(user.getEmail(), subject, body);
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
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Optional<User>> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.getUserByUsername(username);
        if (user.isPresent()) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
