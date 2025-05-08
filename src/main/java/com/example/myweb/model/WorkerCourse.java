package com.example.myweb.model;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "course_detail")
public class 	WorkerCourse {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String title;
	private String description;
	private String category;
	private String price;
	private String content;
	private String phoneNumber;

	@ElementCollection
	@CollectionTable(name = "course_detail_chatbox", joinColumns = @JoinColumn(name = "course_detail_id"))
	private List<String> chatbox;

	@ElementCollection
	@CollectionTable(name = "course_purchases", joinColumns = @JoinColumn(name = "course_id"))
	private List<String> purchasedByEmails;


	private String duration;
	private String language;
	private String email;
	private String fileName;
	private String uploadedBy;

	// Getters and Setters

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public String getCategory() { return category; }
	public void setCategory(String category) { this.category = category; }

	public String getPrice() { return price; }
	public void setPrice(String price) { this.price = price; }

	public String getContent() { return content; }
	public void setContent(String content) { this.content = content; }


	public String getPhoneNumber() { return phoneNumber; }
	public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

	public List<String> getChatbox() { return chatbox; }
	public void setChatbox(List<String> chatbox) { this.chatbox = chatbox; }

	public List<String> getPurchasedByEmails() {
		return purchasedByEmails;
	}

	public void setPurchasedByEmails(List<String> purchasedByEmails) {
		this.purchasedByEmails = purchasedByEmails;
	}

	public String getDuration() { return duration; }
	public void setDuration(String duration) { this.duration = duration; }

	public String getLanguage() { return language; }
	public void setLanguage(String language) { this.language = language; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getFileName() { return fileName; }
	public void setFileName(String fileName) { this.fileName = fileName; }

	public String getUploadedBy() { return uploadedBy; }
	public void setUploadedBy(String uploadedBy) { this.uploadedBy = uploadedBy; }

	// Constructor
	public WorkerCourse(int id, String title, String description, String category, String price, String content,
						String phoneNumber, List<String> chatbox,List<String> purchasedByEmails, List<String> clientEmailId, String duration,
						String language, String email, String fileName, String uploadedBy) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.category = category;
		this.price = price;
		this.content = content;
		this.phoneNumber = phoneNumber;
		this.chatbox = chatbox;
		this.purchasedByEmails=purchasedByEmails;
		this.duration = duration;
		this.language = language;
		this.email = email;
		this.fileName = fileName;
		this.uploadedBy = uploadedBy;
	}

	public WorkerCourse() {
		// Default constructor
	}

	@Override
	public String toString() {
		return "WorkerCourse [id=" + id + ", title=" + title + ", description=" + description + ", category=" + category
				+ ", price=" + price + ", content=" + content + ", phoneNumber=" + phoneNumber + ", chatbox=" + chatbox
				+ ", purchasedByEmails="+purchasedByEmails+  ", duration=" + duration + ", language=" + language + ", email=" + email
				+ ", fileName=" + fileName + ", uploadedBy=" + uploadedBy + "]";
	}
}
