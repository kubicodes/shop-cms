package com.kubicodes.shopcms.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kubicodes.shopcms.models.CategoryRepository;
import com.kubicodes.shopcms.models.ProductRepository;
import com.kubicodes.shopcms.models.data.Category;
import com.kubicodes.shopcms.models.data.Product;

@Controller
@RequestMapping("/admin/products")
public class AdminProductsController {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@GetMapping
	public String index(Model model) {

		List<Product> products = productRepository.findAll();

		List<Category> categories = categoryRepository.findAll();
		HashMap<Integer, String> cats = new HashMap<Integer, String>();
		
		for (Category category : categories) {
			cats.put(category.getId(), category.getTitle());
		}

		model.addAttribute("products", products);
		model.addAttribute("cats", cats);
		return "admin/products/index";
	}

	@GetMapping("/add")
	public String add(Product product, Model model) {

		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);

		return "admin/products/add";
	}

	@PostMapping("/add")
	public String add(@Valid Product product, BindingResult bindingResult, RedirectAttributes redirectAttributes,
			MultipartFile file, Model model) throws IOException {

        List<Category> categories = categoryRepository.findAll();

		// necessary for Error handling
		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", categories);
			return "admin/products/add";
		}

		// image upload
		boolean isFileOk = false;
		byte[] bytes = file.getBytes();
		String fileName = file.getOriginalFilename();
		Path path = Paths.get("src/main/resources/static/img/" + fileName);

		// image upload-validation for png or jpg
		if (fileName.endsWith("png") || fileName.endsWith("jpg")) {
			isFileOk = true;
		}

		// Success message when form redirects.
		redirectAttributes.addFlashAttribute("message", "Produkt erfolgreich hinzugefügt");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		// Logic for automatic slug generation and non-double slug validation
		String slug = product.getName().toLowerCase().replace(" ", "-");
		Product productExists = productRepository.findBySlug(slug);

		if(!isFileOk) {
			redirectAttributes.addFlashAttribute("message", "Bildformat muss png oder jpg sein");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			redirectAttributes.addFlashAttribute("product", product); //show the content of fields wich user entered
		}
		else if (productExists != null) {
			redirectAttributes.addFlashAttribute("message", "Produkt existiert bereits. Bitte ändern");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			redirectAttributes.addFlashAttribute("product", product); // show the content of fields wich user entered			
		} 
		else {
			product.setSlug(slug);
			product.setImage(fileName);
			productRepository.save(product);
			Files.write(path, bytes);
		}

		return "redirect:/admin/products/add";
	}
}
