package com.RaceReserve.Kartland.kartland_repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RaceReserve.Kartland.kartland_entity.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {
	
}