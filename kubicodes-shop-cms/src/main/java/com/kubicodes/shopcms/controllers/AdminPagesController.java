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

import com.kubicodes.shopcms.models.PageRepository;
import com.kubicodes.shopcms.models.data.Page;

@Controller
@RequestMapping("/admin/pages")
public class AdminPagesController {

	@Autowired
	private PageRepository pageRepository;

	@GetMapping
	public String index(Model model) {

		// Get all pages from the repository and took them to the model for the view
		List<Page> allPages = pageRepository.findAll();
		model.addAttribute("allPages", allPages);

		return "admin/pages/index";
	}

	@GetMapping("/add")
	public String addPage(@ModelAttribute Page page) {

		return "admin/pages/add";
	}

	@PostMapping("/add")
	public String addPage(@Valid Page page, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		// necessary for error handling
		if (bindingResult.hasErrors()) {
			return "admin/pages/add";
		}

		// Success message when form redirects.
		redirectAttributes.addFlashAttribute("message", "Seite hinzugefügt.");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		// Logic for automatic slug generation and non-double slug validation
		String slug = page.getSlug();

		if (slug.isEmpty()) {
			slug = page.getTitle().toLowerCase().replace(" ", "-");
		} else {
			slug = page.getSlug().toLowerCase().replace(" ", "-");
		}

		Page slugExists = pageRepository.findBySlug(slug);

		if (slugExists != null) {
			redirectAttributes.addFlashAttribute("message", "Slug für URL existiert bereits. Bitte ändern");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			redirectAttributes.addFlashAttribute("page", page); // show the content of fields wich user entered
		} else {
			page.setSlug(slug);
			pageRepository.save(page);
		}

		return "redirect:/admin/pages/add";
	}

	@GetMapping("/delete/{id}")
	public String deletePage(@PathVariable int id, RedirectAttributes redirectAttributes) {

		// Delete by Id from the url
		pageRepository.deleteById(id);

		// Success Message for the view when redirects
		redirectAttributes.addFlashAttribute("message", "Seite wurde gelöscht");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		return "redirect:/admin/pages";
	}

	@GetMapping("/update/{id}")
	public String updatePage(@PathVariable int id, Model model) {

		Page page = pageRepository.getOne(id);

		model.addAttribute("page", page);

		return "/admin/pages/update";
	}

	@PostMapping("/update")
	public String updatePage(@Valid Page page, BindingResult bindingResult, RedirectAttributes redirectAttributes,
			Model model) {

		Page currentPage = pageRepository.getOne(page.getId());

		//necessary for error handling
		if (bindingResult.hasErrors()) {
			model.addAttribute("pageTitle", currentPage.getTitle());
			return "admin/pages/update";
		}
		//Success message when form redirects.
		redirectAttributes.addFlashAttribute("message", "Seite erfolgreich aktualisiert");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		// Logic for automatic slug generation and non-double slug validation
		String slug = page.getSlug();

		if (slug.isEmpty()) {
			slug = page.getTitle().toLowerCase().replace(" ", "-");
		} else {
			slug = page.getSlug().toLowerCase().replace(" ", "-");
		}

		Page slugExists = pageRepository.findBySlugAndIdNot(slug, page.getId());

		if (slugExists != null) {
			redirectAttributes.addFlashAttribute("message", "Slug für URL existiert bereits. Bitte ändern");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			redirectAttributes.addFlashAttribute("page", page); // show the content of fields wich user entered
		} else {
			page.setSlug(slug);
			pageRepository.save(page);
		}

		return "redirect:/admin/pages/update/" + page.getId();
	}
}
