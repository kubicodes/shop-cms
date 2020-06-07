package com.kubicodes.shopcms.controllers;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kubicodes.shopcms.models.ProductRepository;
import com.kubicodes.shopcms.models.data.Cart;
import com.kubicodes.shopcms.models.data.Product;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	private ProductRepository productRepository;
	
	@GetMapping("/add/{id}")
	public String add(@PathVariable int id, HttpSession session) {
		
		//Get product by Id
		Product product = productRepository.getOne(id);
		
		//check if session is set
		if(session.getAttribute("cart") == null) {
			//when session is not set, add product to the cart and set session
			HashMap<Integer,Cart> cart = new HashMap<>();
			cart.put(id, new Cart(id, product.getName(),product.getPrice(),1,product.getImage()));
			session.setAttribute("cart", cart);

		}else {
			//when session is set, check if this product is added to cart. 
			@SuppressWarnings("unchecked")
			HashMap<Integer, Cart> cart = (HashMap<Integer,Cart>)session.getAttribute("cart");
			
			//If added to cart, increase quantity,
			if(cart.containsKey(id)) {
				int quantity = cart.get(id).getQuantity();
				cart.put(id, new Cart(id, product.getName(),product.getPrice(),++quantity,product.getImage()));
			}else {
				//if not add this product and set session
				cart.put(id, new Cart(id, product.getName(),product.getPrice(),1,product.getImage()));
				session.setAttribute("cart", cart);
			}

		}
		
		
		return null;
		
	}

}
