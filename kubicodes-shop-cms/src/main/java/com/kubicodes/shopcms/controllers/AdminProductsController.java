package com.kubicodes.shopcms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kubicodes.shopcms.models.ProductRepository;
import com.kubicodes.shopcms.models.data.Product;

@Controller
@RequestMapping("/admin/products")
public class AdminProductsController {

	@Autowired
	ProductRepository productRepository;
	
	@GetMapping
	public String index(Model model) {
		
		List<Product> products = productRepository.findAll();
		model.addAttribute("products", products);
		
		return "admin/products/index";
	}
}
