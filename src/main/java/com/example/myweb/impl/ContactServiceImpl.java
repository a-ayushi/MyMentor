package com.example.myweb.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myweb.model.ContactNo;
import com.example.myweb.repository.ContactRepo;
import com.example.myweb.service.ContactService;

@Service
public class ContactServiceImpl implements ContactService{

	@Autowired
	private ContactRepo repo;
	
	@Override
	public ContactNo saveContact(ContactNo no) {
		// Auto-generated method stub
		return repo.save(no);
	}
}
