package com.example.myweb.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myweb.model.UserD;
import com.example.myweb.repository.UserRepo;
import com.example.myweb.service.UserService;
import java.util.List;

@Service
public class UserServiceImplement implements UserService {

	@Autowired
	private UserRepo repo;

	@Override
	public UserD createUser(UserD userD) {

		return repo.save(userD);
	}

	@Override
	public boolean checkEmail(String email) {

		return repo.existsByEmail(email);
	}

	@Override
	public UserD logIn(String role, String email, String password) {
		return repo.findByRoleAndEmailAndPassword(role, email,password);
	}

	@Override
	public long countUsersByRole(String role) {
        return repo.countByRoleIgnoreCase(role);
    }

	@Override
	public List getUsersByRole(String role) {
		return (List<UserD>) repo.findByRoleIgnoreCase(role);
	}
	
	

}
