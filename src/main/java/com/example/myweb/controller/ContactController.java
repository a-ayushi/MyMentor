package com.example.myweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.myweb.model.ContactNo;
import com.example.myweb.service.ContactService;
import com.example.myweb.service.UserService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import jakarta.servlet.http.HttpSession;

@Controller
public class ContactController {

	@Autowired
	private ContactService service;

	@GetMapping("/contact")
	public String contact(Model model) {
		model.addAttribute("contact", new ContactNo());
		return "contact"; // Renders contact.html
	}

	@PostMapping("/contact")
	public String contact(RedirectAttributes redirectAttributes, ContactNo no) {

		ContactNo saveContact = service.saveContact(no);
		if (saveContact != null) {
			redirectAttributes.addFlashAttribute("success","Thank you for contact us..");
		} else {
			redirectAttributes.addFlashAttribute("error", "something went wrong");
		}
		System.out.println(saveContact);
		return "redirect:/contact";
	}

}
