package com.RaceReserve.Kartland.kartland_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.RaceReserve.Kartland.kartland_entity.Kart;
import com.RaceReserve.Kartland.kartland_repository.KartRepository;
import com.RaceReserve.Kartland.kartland_service.KartService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/karts")
// @CrossOrigin(origins = "http://localhost:4200","https://targettallyarena.com") // Adjust based on frontend
@CrossOrigin(origins = {
    "https://targettallyarena.com",
    "https://www.targettallyarena.com",
    "http://localhost:4200",
    "https://kartlandindia.com",
    "https://www.kartlandindia.com"
})
    
    public class KartController {

    @Autowired
    private KartService kartService;
    private final KartRepository kartRepository;
    
    @Autowired
    public KartController(KartRepository kartRepository) {
        this.kartRepository = kartRepository;
    }


    // Get all karts
    @GetMapping
    public ResponseEntity<List<Kart>> getAllKarts() {
        return ResponseEntity.ok(kartService.getAllKarts());
    }

    // Get kart by ID
    @GetMapping("/{id}")
    public ResponseEntity<Kart> getKartById(@PathVariable Long id) {
        Optional<Kart> kart = kartService.getKartById(id);
        return kart.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get available karts
    @GetMapping("/available")
    public ResponseEntity<List<Kart>> getAvailableKarts() {
        return ResponseEntity.ok(kartService.getAvailableKarts());
    }

    // Get karts by type
    @GetMapping("/type/{kartType}")
    public ResponseEntity<List<Kart>> getKartsByType(@PathVariable String kartType) {
        return ResponseEntity.ok(kartService.getKartsByType(kartType));
    }

    // Add a new kart
    @PostMapping
    public ResponseEntity<Kart> addKart(@RequestBody Kart kart) {
        return ResponseEntity.ok(kartService.addKart(kart));
    }

    // Update kart details
    @PutMapping("/{id}")
    public ResponseEntity<Kart> updateKart(@PathVariable Long id, @RequestBody Kart updatedKart) {
        Kart kart = kartService.updateKart(id, updatedKart);
        return kart != null ? ResponseEntity.ok(kart) : ResponseEntity.notFound().build();
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetKarts() {
        // Fetch all karts from DB
        List<Kart> karts = kartRepository.findAll();

        // Reset each kart
        for (Kart kart : karts) {
            kart.setAvailable(true);  // Set available to true
            kart.setStartTime(null);  // Reset start time
            kart.setEndTime(null);    // Reset end time
        }

        // Save updated karts
        kartRepository.saveAll(karts);

        return ResponseEntity.ok("All karts have been reset successfully!");
    }
    
}
