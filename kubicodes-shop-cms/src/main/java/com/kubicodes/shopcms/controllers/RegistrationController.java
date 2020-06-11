package com.kubicodes.shopcms.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kubicodes.shopcms.models.UserRepository;
import com.kubicodes.shopcms.models.data.User;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired //bean has to be created by own. Do this in the Securityconfig
	private PasswordEncoder passwordEncoder;
	
	@GetMapping
	public String register(User user) {
		
		return "register";
		
	}
	
	@PostMapping
	public String register(@Valid User user, BindingResult bindingResult, Model model) {
		
		//if there are errors redirect to register page
		if(bindingResult.hasErrors()) {
			return "register";
		}
		
		if(! user.getPassword().equals(user.getConfirmPassword())) {
			model.addAttribute("passwordMatchError", "Passwörter stimmen nicht überein");
			return "register";
		}
		
		//encode the password so its not visible in the DB as the user entered
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		//save the user
		userRepository.save(user);
		
		//redirect to login page after registration
		return "redirect:/login";
	}
	
}
