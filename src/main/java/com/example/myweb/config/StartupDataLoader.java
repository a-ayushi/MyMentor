package com.example.myweb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.myweb.model.UserD;
import com.example.myweb.service.UserService;

@Component
public class StartupDataLoader implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        // Check if admin user already exists
        boolean adminExists = userService.checkEmail("admin@gmail.com");

        if (!adminExists) {
            // Create admin user
            UserD admin = new UserD();
            admin.setEmail("admin@gmail.com");
            admin.setPassword("admin"); // You may want to hash the password
            admin.setRole("admin");
            admin.setFullName("Administrator");
            admin.setContactNumber("1234567890");

            userService.createUser(admin);
            System.out.println("Admin user created: admin@gmail.com");
        } else {
            System.out.println("Admin user already exists.");
        }
    }
}