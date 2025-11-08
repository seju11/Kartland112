package com.RaceReserve.Kartland.kartland_service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RaceReserve.Kartland.kartland_entity.Booking;
import com.RaceReserve.Kartland.kartland_repository.BookingRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // Add a new booking
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    // Get all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Get booking by ID
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }


    // Cancel a booking
    public void cancelBooking(Long id) {
        bookingRepository.deleteById(id);
    }
    
    public Optional<Booking> getBookingByName(String customerName) {
        return bookingRepository.findByCustomerName(customerName);
    }

    public boolean cancelBookingByName(String customerName) {
        Optional<Booking> booking = bookingRepository.findByCustomerName(customerName);
        if (booking.isPresent()) {
            bookingRepository.delete(booking.get());
            return true;
        }
        return false;
    }
}
