package com.RaceReserve.Kartland.kartland_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RaceReserve.Kartland.kartland_entity.Kart;
import com.RaceReserve.Kartland.kartland_repository.KartRepository;

import java.util.List;
import java.util.Optional;

@Service
public class KartService {

    @Autowired
    private KartRepository kartRepository;

    public List<Kart> getAllKarts() {
        return kartRepository.findAll();
    }

    public Optional<Kart> getKartById(Long id) {
        return kartRepository.findById(id);
    }

    public List<Kart> getKartsByType(String kartType) {
        return kartRepository.findByKartType(kartType);
    }

    public List<Kart> getAvailableKarts() {
        return kartRepository.findByAvailable(true);
    }

    public Kart addKart(Kart kart) {
        return kartRepository.save(kart);
    }

    public Kart updateKart(Long id, Kart updatedKart) {
        Optional<Kart> existingKartOpt = kartRepository.findById(id);
        if (existingKartOpt.isPresent()) {
            Kart existingKart = existingKartOpt.get();
            existingKart.setKartType(updatedKart.getKartType());
            existingKart.setAvailable(updatedKart.isAvailable());
            existingKart.setPrice5Laps(updatedKart.getPrice5Laps());
            existingKart.setPrice10Laps(updatedKart.getPrice10Laps());
            existingKart.setPrice20Laps(updatedKart.getPrice20Laps());
            existingKart.setStartTime(updatedKart.getStartTime());
            existingKart.setEndTime(updatedKart.getEndTime());
            return kartRepository.save(existingKart);
        }
        return null;
    }

    public void deleteKart(Long id) {
        kartRepository.deleteById(id);
    }
}
