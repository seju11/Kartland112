package com.RaceReserve.Kartland.kartland_entity;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "karts")
public class Kart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String kartType; // Single-seater or Two-seater
    private boolean available;

    private double price5Laps;
    private double price10Laps;
    private double price20Laps;

    private LocalTime startTime;
    private LocalTime endTime;

    public Kart() {}

    public Kart(String kartType, boolean available, double price5Laps, double price10Laps, double price20Laps, LocalTime startTime, LocalTime endTime) {
        this.kartType = kartType;
        this.available = available;
        this.price5Laps = price5Laps;
        this.price10Laps = price10Laps;
        this.price20Laps = price20Laps;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKartType() {
        return kartType;
    }

    public void setKartType(String kartType) {
        this.kartType = kartType;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public double getPrice5Laps() {
        return price5Laps;
    }

    public void setPrice5Laps(double price5Laps) {
        this.price5Laps = price5Laps;
    }

    public double getPrice10Laps() {
        return price10Laps;
    }

    public void setPrice10Laps(double price10Laps) {
        this.price10Laps = price10Laps;
    }

    public double getPrice20Laps() {
        return price20Laps;
    }

    public void setPrice20Laps(double price20Laps) {
        this.price20Laps = price20Laps;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
