package com.RaceReserve.Kartland.kartland_controller;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.RaceReserve.Kartland.kartland_entity.Booking;
import com.RaceReserve.Kartland.kartland_entity.User;
import com.RaceReserve.Kartland.kartland_service.BookingService;
import com.RaceReserve.Kartland.kartland_service.EmailService;
import com.RaceReserve.Kartland.kartland_repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
// @CrossOrigin(origins = "https://targettallyarena.com")
@CrossOrigin(origins = {
    "https://targettallyarena.com",
    "https://www.targettallyarena.com",
    "http://localhost:4200",
    "https://kartlandindia.com",
    "https://www.kartlandindia.com"
})
    
    public class BookingController {

    private final BookingService bookingService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        if (booking.getUsername() == null || booking.getUsername().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is required for booking.");
        }

        Optional<User> existingUser = userRepository.findByUsername(booking.getUsername());
        if (existingUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Booking failed: User must be registered before booking.");
        }

        booking.setUser(existingUser.get());
        Booking savedBooking = bookingService.createBooking(booking);

        String customerName = savedBooking.getCustomerName();
        String bookingDate = savedBooking.getDate();
        int numberOfPeople = savedBooking.getNumberOfPeople();

        // Format LocalDateTime to readable string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String formattedTime = savedBooking.getTime() != null
                ? savedBooking.getTime().format(formatter)
                : "Not Available";

        // Send confirmation to user
        String subject = "Booking Confirmation - Kartland";
        String message = "Hi " + customerName + ",\n\n"
                + "Your booking has been successfully confirmed for " + bookingDate + " at " + formattedTime + ".\n"
                + "Number of People: " + numberOfPeople + "\n\n"
                + "Thank you for choosing Kartland!\n\n"
                + "RaceReserve Team";

        emailService.sendSimpleMessage(savedBooking.getEmail(), subject, message);

     // Send notification to owner
        String ownerEmail = "s3458540@gmail.com";
        String ownerSubject = "New Booking Notification - Kartland";

        // Include the contact number in the email message
        String ownerMessage = "A new booking has been made:\n\n"
                + "Customer Name: " + customerName + "\n"
                + "Booking Date: " + bookingDate + "\n"
                + "Booking Time: " + formattedTime + "\n"
                + "Number of People: " + numberOfPeople + "\n"
                + "Customer Email: " + savedBooking.getEmail() + "\n"
                + "Customer Mobile: " + savedBooking.getContactNumber() + "\n\n" // Added mobile number
                + "Thank you,\nRaceReserve Team";

        // Send email to owner
        emailService.sendSimpleMessage(ownerEmail, ownerSubject, ownerMessage);

        return ResponseEntity.ok(savedBooking);

    }
    
}
