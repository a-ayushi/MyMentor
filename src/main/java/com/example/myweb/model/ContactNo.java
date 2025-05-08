package com.example.myweb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "contactUs")
public class ContactNo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String fullName;

	private String email;

	private String message;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ContactNo(int id, String fullName, String email, String message) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.message = message;
	}

	public ContactNo() {
		super();
		// Auto-generated constructor stub
	}


}
