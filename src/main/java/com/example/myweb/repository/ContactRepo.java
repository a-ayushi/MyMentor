package com.example.myweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myweb.model.ContactNo;

public interface ContactRepo extends JpaRepository<ContactNo, Integer>{

}
