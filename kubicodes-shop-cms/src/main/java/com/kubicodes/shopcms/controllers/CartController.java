package com.kubicodes.shopcms.controllers;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kubicodes.shopcms.models.CategoryRepository;
import com.kubicodes.shopcms.models.ProductRepository;
import com.kubicodes.shopcms.models.data.Cart;
import com.kubicodes.shopcms.models.data.Category;
import com.kubicodes.shopcms.models.data.Product;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@GetMapping("/add/{id}")
	public String add(@PathVariable int id, HttpSession session, Model model,
			@RequestParam(value = "cartPage", required = false) String cartPage) {

		// Get product by Id
		Product product = productRepository.getOne(id);

		// check if session is set
		if (session.getAttribute("cart") == null) {
			// when session is not set, add product to the cart and set session
			HashMap<Integer, Cart> cart = new HashMap<>();
			cart.put(id, new Cart(id, product.getName(), product.getPrice(), 1, product.getImage()));
			session.setAttribute("cart", cart);

		} else {
			// when session is set, check if this product is added to cart.
			@SuppressWarnings("unchecked")
			HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");

			// If added to cart, increase quantity,
			if (cart.containsKey(id)) {
				int quantity = cart.get(id).getQuantity();
				cart.put(id, new Cart(id, product.getName(), product.getPrice(), ++quantity, product.getImage()));
			} else {
				// if not add this product and set session
				cart.put(id, new Cart(id, product.getName(), product.getPrice(), 1, product.getImage()));
				session.setAttribute("cart", cart);
			}

		}

		@SuppressWarnings("unchecked")
		HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");
		int size = 0;
		double total = 0;

		// loop through the values of the hashmap cart and add the quantity to size and
		// the
		// value for the price to the total
		for (Cart value : cart.values()) {
			size += value.getQuantity();
			total += value.getQuantity() * Double.parseDouble(value.getPrice());
		}

		// add size and total to model
		model.addAttribute("size", size);
		model.addAttribute("total", total);

		// cartPage != null -> user is on the cart page
		if (cartPage != null) {
			return "redirect:/cart/view";
		}

		return "cart-view";

	}

	// create method for viewing the cart page
	@GetMapping("/view")
	public String viewCart(Model model, HttpSession session) {

		// if session is not set redirect to homepage
		if (session.getAttribute("cart") == null) {
			return "redirect:/";
		}

		// if session is set get the session as hashmap
		@SuppressWarnings("unchecked")
		HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");

		// add cart to the model
		model.addAttribute("cart", cart);

		// necessary for categories on sidebar
		List<Category> allCategories = categoryRepository.findAll();
		model.addAttribute("allCategories", allCategories);

		// send attribute to the model with value true to use it in de cart-frag for
		// displaying frag on the sidebar just when
		// user is not on the cart page
		model.addAttribute("notCartViewPage", true);

		return "cart";
	}

	@GetMapping("/subtract/{id}")
	public String subtract(@PathVariable int id, HttpSession session, HttpServletRequest httpServletRequest) {

		// First get the product
		Product product = productRepository.getOne(id);

		// Get the items of the session
		@SuppressWarnings("unchecked")
		HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");

		// Get the quantity of the product
		int quantity = cart.get(id).getQuantity();

		// if quantity is 1, delete the product from the cart session, else subtract
		// quantity for 1
		if (quantity == 1) {
			cart.remove(id);
			if (cart.size() == 0) {
				session.removeAttribute("cart");
			}
		} else {
			cart.put(id, new Cart(id, product.getName(), product.getPrice(), --quantity, product.getImage()));
		}

		// Get the referer link dynamically
		String refererLink = httpServletRequest.getHeader("referer");

		return "redirect:" + refererLink;

	}

	@GetMapping("remove/{id}")
	public String remove(@PathVariable int id, HttpSession session, HttpServletRequest httpServletRequest) {

		// Get the items of the session
		@SuppressWarnings("unchecked")
		HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");

		// Remove the item with this id
		cart.remove(id);

		// if cart is empty remove the session
		if (cart.size() == 0) {
			session.removeAttribute("cart");
		}

		// Get the referer link dynamically
		String refererLink = httpServletRequest.getHeader("referer");

		return "redirect:" + refererLink;
	}

	@GetMapping("/clear")
	public String remove(HttpSession session, HttpServletRequest httpServletRequest) {
		
		//Remove the session to clear the cart
		session.removeAttribute("cart");
		
		// Get the referer link dynamically
		String refererLink = httpServletRequest.getHeader("referer");

		return "redirect:" + refererLink;
	}

}
