package com.kubicodes.shopcms.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kubicodes.shopcms.models.CategoryRepository;
import com.kubicodes.shopcms.models.data.Category;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoriesController {

	@Autowired
	CategoryRepository categoryRepository;
	
	@GetMapping
	public String index(Model model) {
		
		// Get all categories from the repository and took them to the model for the view
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);
		
		return "/admin/categories/index";
	}
	
	@GetMapping("/add")
	public String addCategory(@ModelAttribute Category category) {
	
		return "/admin/categories/add";
	}
	
	@PostMapping("/add")
	public String addCategory(@Valid Category category, BindingResult bindingResult, 
			RedirectAttributes redirectAttributes) {
		
		// necessary for error handling
		if(bindingResult.hasErrors()) {
			return "admin/categories/add";
		}
		
		// Success message when form redirects.
		redirectAttributes.addFlashAttribute("message", "Seite erfolgreich hinzugefügt");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		
		// Logic for automatic slug generation and non-double slug validation
		String slug = category.getSlug();
		
		if(slug.isEmpty()) {
			slug = category.getTitle().toLowerCase().replace(" ", "-");
		}else {
			slug = category.getSlug().toLowerCase().replace(" ", "-");
		}
		
		Category slugExists = categoryRepository.findBySlug(slug);
		
		if(slugExists != null) {
			redirectAttributes.addFlashAttribute("message", "Slug für URL existiert bereits. Bitte ändern");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			redirectAttributes.addFlashAttribute("category", category);
		}else {
			category.setSlug(slug);
			categoryRepository.save(category);

		} 
		return "redirect:/admin/categories/add";
	}
}
