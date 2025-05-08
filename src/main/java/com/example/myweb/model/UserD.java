package com.example.myweb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;          //  ← add if you want to tweak the column
import jakarta.persistence.Table;

@Entity
@Table(name = "user_Details")
public class UserD {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String fullName;
    private String email;
    private String password;
    private String confirmPassword;
    private String role;
    private String contactNumber;
    private boolean terms;

    /* ------------------ NEW FIELD ------------------ */
    @Column(length = 32)          // optional – tweak size/nullability as you like
    private String speciality;    // e.g. "career", "psychologist", …

    /* --------------- getters & setters ------------- */

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public boolean isTerms() { return terms; }
    public void setTerms(boolean terms) { this.terms = terms; }


    public String getSpeciality() { return speciality; }
    public void setSpeciality(String speciality) { this.speciality = speciality; }

    /* ----------------- constructors ---------------- */

    public UserD() { /* no-arg constructor for JPA */ }

    public UserD(int id, String fullName, String email, String password,
                 String confirmPassword, String role, String contactNumber,
                 boolean terms, String speciality) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.role = role;
        this.contactNumber = contactNumber;
        this.terms = terms;
        this.speciality = speciality;
    }

    /* ------------------- toString ------------------- */

    @Override
    public String toString() {
        return "UserD{" +
            "id=" + id +
            ", fullName='" + fullName + '\'' +
            ", email='" + email + '\'' +
            ", role='" + role + '\'' +
            ", speciality='" + speciality + '\'' +
            '}';
    }
}
