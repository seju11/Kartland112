package com.RaceReserve.Kartland.kartland_controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RaceReserve.Kartland.kartland_entity.ContactRequest;
import com.RaceReserve.Kartland.kartland_service.EmailService;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "http://localhost:4200")
public class ContactController {

    @Autowired
    private EmailService mailService;

    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> sendContact(@RequestBody ContactRequest request) {
        Map<String, String> response = new HashMap<>();
        try {
            mailService.sendContactMessage(request.getName(), request.getEmail(), request.getMessage());
            response.put("message", "Message sent successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Failed to send message.");
            return ResponseEntity.status(500).body(response);
        }
    }
}
