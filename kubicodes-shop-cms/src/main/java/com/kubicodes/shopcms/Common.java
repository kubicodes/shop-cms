package com.kubicodes.shopcms;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.kubicodes.shopcms.models.data.Cart;

@ControllerAdvice
public class Common {

	@ModelAttribute
	public void sharedData(Model model, HttpSession session) {

		// set boolean to check if cart is active (default: false)
		boolean cartActive = false;

		// check if cart session is set
		if (session.getAttribute("cart") != null) {

			// get session cart
			@SuppressWarnings("unchecked")
			HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");

			// define variables for size of items and the total value of the cart
			int size = 0;
			double total = 0;

			//loop through the values of the hashmap cart and add the quantity to size and the 
			//value for the price to the total
			for (Cart value : cart.values()) {
				size += value.getQuantity();
				total += value.getQuantity() * Double.parseDouble(value.getPrice());
			}
			
			//add size and total to model
			model.addAttribute("commonSize", size);
			model.addAttribute("commonTotal", total);
			
			//set cartactive to true
			cartActive = true;
			
		}
		
		//add cartactive to model
		model.addAttribute("cartActive", cartActive);

	}
}
