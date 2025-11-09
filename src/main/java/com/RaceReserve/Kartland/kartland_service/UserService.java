package com.RaceReserve.Kartland.kartland_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.RaceReserve.Kartland.kartland_entity.User;
import com.RaceReserve.Kartland.kartland_repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register a new user (encode password before saving)
    public String registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "User already exists with this email!";
        }

        // Encode the password before saving (for new users)
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully!";
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // ✅ Improved Login logic (handles both encoded and plain)
    public String loginUser(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String storedPassword = user.getPassword();

            // Handle both BCrypt and plain-text passwords
            boolean isPasswordValid;
            if (storedPassword.startsWith("$2a$")) {
                isPasswordValid = passwordEncoder.matches(password, storedPassword);
            } else {
                isPasswordValid = password.equals(storedPassword);
            }

            if (isPasswordValid) {
                return "Login success";
            }
        }

        return "Invalid email or password!";
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
