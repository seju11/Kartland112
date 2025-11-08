package com.RaceReserve.Kartland.kartland_repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RaceReserve.Kartland.kartland_entity.Kart;

import java.util.List;

@Repository
public interface KartRepository extends JpaRepository<Kart, Long> {
    List<Kart> findByKartType(String kartType);
    List<Kart> findByAvailable(boolean available);
}