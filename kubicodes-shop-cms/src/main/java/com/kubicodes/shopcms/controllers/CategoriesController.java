package com.kubicodes.shopcms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kubicodes.shopcms.models.CategoryRepository;
import com.kubicodes.shopcms.models.PageRepository;
import com.kubicodes.shopcms.models.ProductRepository;
import com.kubicodes.shopcms.models.data.Category;
import com.kubicodes.shopcms.models.data.Page;
import com.kubicodes.shopcms.models.data.Product;

@Controller
@RequestMapping("/kategorien")
public class CategoriesController {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private PageRepository pageRepository;
	
	@GetMapping("/{slug}")
	public String showProducts(@PathVariable String slug, Model model) {
		
		//Get all Products and Categories
		List<Product> allProducts = productRepository.findAll();
		List<Category> allCategories = categoryRepository.findAll();
		List<Page> allPages = pageRepository.findAll();
		
		//necessary for sidebar and navbar
		model.addAttribute("allCategories", allCategories);
		model.addAttribute("allPages", allPages);
		
		//Add all Products to the model
		if(slug.equals("all")) {
			model.addAttribute("allProducts", allProducts);
		}else {
			Category category = categoryRepository.findBySlug(slug);
			int categoryId = category.getId();
			String categoryTitle = category.getTitle();
			List<Product> products = productRepository.findAllByCategoryId(Integer.toString(categoryId));
			
			model.addAttribute("categoryTitle", categoryTitle);
			model.addAttribute("allProducts", products);
		}
		
		return "products";
	}

}
