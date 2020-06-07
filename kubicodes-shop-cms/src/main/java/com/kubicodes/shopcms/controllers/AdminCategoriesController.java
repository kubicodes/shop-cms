package com.kubicodes.shopcms.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kubicodes.shopcms.models.CategoryRepository;
import com.kubicodes.shopcms.models.data.Category;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoriesController {

	@Autowired
	private CategoryRepository categoryRepository;

	@GetMapping
	public String index(Model model) {

		// Get all categories from the repository and took them to the model for the
		// view
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);

		return "/admin/categories/index";
	}

	// Add Functionality for categories
	@GetMapping("/add")
	public String addCategory(@ModelAttribute Category category) {

		return "admin/categories/add";
	}

	@PostMapping("/add")
	public String addCategory(@Valid Category category, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {

		// necessary for error handling
		if (bindingResult.hasErrors()) {
			return "admin/categories/add";
		}

		// Success message when form redirects.
		redirectAttributes.addFlashAttribute("message", "Seite erfolgreich hinzugefügt");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		// Logic for automatic slug generation and non-double slug validation
		String slug = category.getSlug();

		if (slug.isEmpty()) {
			slug = category.getTitle().toLowerCase().replace(" ", "-");
		} else {
			slug = category.getSlug().toLowerCase().replace(" ", "-");
		}

		Category slugExists = categoryRepository.findBySlug(slug);

		if (slugExists != null) {
			redirectAttributes.addFlashAttribute("message", "Slug für URL existiert bereits. Bitte ändern");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			redirectAttributes.addFlashAttribute("category", category);
		} else {
			category.setSlug(slug);
			categoryRepository.save(category);

		}
		return "redirect:/admin/categories/add";
	}

	// Delete Functionality for categories
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) {

		// Delete by id from the url
		categoryRepository.deleteById(id);

		// Success Message for the view when redirects
		redirectAttributes.addFlashAttribute("message", "Kategorie wurde gelöscht");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		return "redirect:/admin/categories";
	}

	// Update Functionality for categories
	@GetMapping("/update/{id}")
	public String update(@PathVariable int id, Model model) {
		Category category = categoryRepository.getOne(id);
		model.addAttribute("category", category);

		return "admin/categories/update";
	}

	@PostMapping("/update")
	public String update(@Valid Category category, BindingResult bindingResult, RedirectAttributes redirectAttributes,
			Model model) {

		Category currentCategory = categoryRepository.getOne(category.getId());
		
		//necessary for error handling
		if (bindingResult.hasErrors()) {
			model.addAttribute("categoryTitle", currentCategory.getTitle());
			return "admin/categories/update";
		}

		//Success message when form redirects.
		redirectAttributes.addFlashAttribute("message", "Kategorie erfolgreich aktualisiert");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		// Logic for automatic slug generation and non-double slug validation
		String slug = category.getSlug();

		if (slug.isEmpty()) {
			slug = category.getTitle().toLowerCase().replace(" ", "-");
		} else {
			slug = category.getSlug().toLowerCase().replace(" ", "-");
		}

		Category slugExists = categoryRepository.findBySlugAndIdNot(slug, category.getId());

		if (slugExists != null) {
			redirectAttributes.addFlashAttribute("message", "Slug für URL existiert bereits. Bitte ändern");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			redirectAttributes.addFlashAttribute("category", category);
		} else {
			category.setSlug(slug);
			categoryRepository.save(category);

		}
		
		return "redirect:/admin/categories/update/" + category.getId();
	}
}
