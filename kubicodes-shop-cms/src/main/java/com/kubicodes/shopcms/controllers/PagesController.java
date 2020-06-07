package com.kubicodes.shopcms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kubicodes.shopcms.models.PageRepository;
import com.kubicodes.shopcms.models.data.Page;

@Controller
@RequestMapping("/")
public class PagesController {

	@Autowired
	private PageRepository pageRepository;
	
	@ModelAttribute
	public void allPages(Model model) {
		
		List<Page> allPages = pageRepository.findAll();
		model.addAttribute("allPages", allPages);
		
	}

	@GetMapping(value = {"" , "/startseite"})
	public String showHome(Model model) {
		
		Page page = pageRepository.findBySlug("startseite");
		model.addAttribute("page", page);
		return "pages";
	}

}
