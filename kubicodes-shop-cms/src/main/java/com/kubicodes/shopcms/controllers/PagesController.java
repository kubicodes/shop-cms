package com.kubicodes.shopcms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kubicodes.shopcms.models.PageRepository;
import com.kubicodes.shopcms.models.data.Page;

@Controller
@RequestMapping("/")
public class PagesController {

	@Autowired
	private PageRepository pageRepository;
	
	@GetMapping
	public String showHome(Model model) {
		
		Page page = pageRepository.findBySlug("startseite");
		model.addAttribute("page", page);
		return "pages";
	}
}
