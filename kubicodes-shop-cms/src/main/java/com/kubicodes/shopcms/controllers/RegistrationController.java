package com.kubicodes.shopcms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kubicodes.shopcms.models.UserRepository;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired //bean has to be created by own. Do this in the Securityconfig
	private PasswordEncoder passwordEncoder;
}
