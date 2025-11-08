package com.RaceReserve.Kartland.kartland_entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
	
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	    private String username;
	    private String contactNo;
	    private String email;
	    private String password;
	    
	    //generate constructors
	    
	    public User() {}

		public User(Long id, String username, String contactNo, String email, String password) {
			super();
			this.id = id;
			this.username = username;
			this.contactNo = contactNo;
			this.email = email;
			this.password = password;
		}
	
	    //generate getters & setters
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getContactNo() {
			return contactNo;
		}

		public void setContactNo(String contactNo) {
			this.contactNo = contactNo;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public void setRole(String string) {
			// TODO Auto-generated method stub
			
		}

}
