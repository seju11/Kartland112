package com.RaceReserve.Kartland.kartland_entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Booking {
	
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	
	private String username;
	private String customerName;
	private String email;
    private String date;
    private LocalDateTime time;
    private int numberOfPeople;
    private String bookingType;
    private String contactNumber;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "kart_id")
    private Kart kart;
    
    public Booking() {}
    
    
	public Booking(Long id, String email, String date, LocalDateTime time, int numberOfPeople, String bookingType, User user, Kart kart, String customerName,String contactNumber, String username ) {
		super();
		this.id = id;
		this.email = email;
		this.date = date;
		this.time = time;
		this.numberOfPeople = numberOfPeople;
		this.bookingType = bookingType;
		this.user = user;
		this.kart = kart;
		this.customerName = customerName;
		this.contactNumber = contactNumber;
		this.username = username;
	}



	// Getters and setters
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
    public String getEmail() {
        return email;
    }

    
	public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Kart getKart() {
		return kart;
	}


	public void setKart(Kart kart) {
		this.kart = kart;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public String getContactNumber() {
		return contactNumber;
	}


	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}


	public String getUsername() {
		return username;
	}


	public void setUserName(String username) {
		this.username = username;
	}


	  
    
}
