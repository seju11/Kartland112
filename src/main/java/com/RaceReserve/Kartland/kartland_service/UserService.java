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

    // Register a new user (No password encoding)
    public String registerUser(User user) {
    	if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "User already exists with this email!";
        }
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

    // User Login (Basic validation)
    public String loginUser(String email, String password) {
    	Optional<User> user = userRepository.findByEmail(email);

    	if (user.isPresent() && user.get().getPassword().equals(password)) {
    	    return "Login success";
    	}
        return "Invalid email or password!";
    }
    
    public void save(User user) {
        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    
}
