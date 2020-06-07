package com.kubicodes.shopcms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kubicodes.shopcms.models.CategoryRepository;
import com.kubicodes.shopcms.models.PageRepository;
import com.kubicodes.shopcms.models.data.Category;
import com.kubicodes.shopcms.models.data.Page;

@Controller
@RequestMapping("/")
public class PagesController {

	@Autowired
	PageRepository pageRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	//Putting all Pages and Categories to the model, for access in views
	@ModelAttribute
	public void allPages(Model model) {
		
		List<Page> allPages = pageRepository.findAll();
		model.addAttribute("allPages", allPages);
	
	}
	
	@ModelAttribute
	public void allCategories(Model model) {
		
		List<Category> allCategories = categoryRepository.findAll();
		model.addAttribute("allCategories", allCategories);
		
	}

	//Index Page
	@GetMapping(value = {"" , "/startseite"})
	public String showHome(Model model) {
		
		Page page = pageRepository.findBySlug("startseite");
		model.addAttribute("page", page);
		return "pages";
	}
	
	//All other pages by Slug
	@GetMapping("/{slug}")
	public String showPage(@PathVariable String slug, Model model) {
		
		Page page = pageRepository.findBySlug(slug);
		
		//redirect to home when some one enters a non-existing page
		if(page == null) {
			return "redirect:/";
		}
		
		//add the page by slug to the model and get access in the view
		model.addAttribute("page", page);
		return "pages";
	}

}
