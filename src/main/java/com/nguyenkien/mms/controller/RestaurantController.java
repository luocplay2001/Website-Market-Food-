package com.nguyenkien.mms.controller;


import java.io.File;
import java.nio.file.Path;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.nguyenkien.mms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.nguyenkien.mms.model.Category;
import com.nguyenkien.mms.model.Customer;
import com.nguyenkien.mms.model.Order;
import com.nguyenkien.mms.model.OrderProduct;
import com.nguyenkien.mms.model.Product;
import com.nguyenkien.mms.model.Restaurant;


@Controller
@RequestMapping("/restaurant")
public class RestaurantController {
	
	@Autowired
	RestaurantService restaurantService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	ShipperService shipperService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ShoppingCartService shoppingCartService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderProductService orderProductService;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	CategoryService categoryService;

	@Autowired
	private EmailService emailService;
	
	public void userRestaurant(Model model,Principal principal) {
		List<Restaurant> restaurants = restaurantService.getAllRestaurants();
		Restaurant restaurant = null;
		if(principal != null) {
			long id = 0l;
			String email = principal.getName();
			for(Restaurant i : restaurants) {
				if(i.getEmail().equalsIgnoreCase(email)) {
					id = i.getRestaurantId();
					break;
				}
			}
			restaurant = restaurantService.getRestaurantById(id);
			model.addAttribute("restaurant", restaurant);
		}
	}
	
	@RequestMapping 
	public String homeRestaurant(Model model,Principal principal) {
		List<Restaurant> restaurants = restaurantService.getAllRestaurants();
		Restaurant restaurant = null;
		if(principal != null) {
			long id = 0l;
			String email = principal.getName();
			for(Restaurant i : restaurants) {
				if(i.getEmail().equalsIgnoreCase(email)) {
					id = i.getRestaurantId();
					break;
				}
			}
			restaurant = restaurantService.getRestaurantById(id);
			model.addAttribute("restaurant", restaurant);
		}
		List<Order> orders = new ArrayList<Order>();
		for(Order order : orderService.getAllOrders()) {
			if((order.getRestaurant().getRestaurantId() == restaurant.getRestaurantId()) &&
					order.getStatus_restaurant() == 0) {
				orders.add(order); 
			}
		}
		List<OrderProduct> orderProducts = orderProductService.getAllOrderProducts();
		model.addAttribute("orderProducts", orderProducts);
		model.addAttribute("orders", orders);
		return "restaurant/homeRestaurant";
	}
	
	@RequestMapping("profile")
	public String profile(Principal principal, Model model) {
		userRestaurant(model, principal);
		return "restaurant/profileRestaurant";
	}
	
	@RequestMapping("uploadProfile")
	public String uploadProfile(Principal principal, Model model) {
		userRestaurant(model, principal);
		return "restaurant/uploadProfileRestaurant";
	}
	
	@PostMapping("uploadProfile/{restaurantId}")
	public String upload(@ModelAttribute Restaurant restaurant) {
		Restaurant r = restaurantService.getRestaurantById(restaurant.getRestaurantId());
		r.setAddress(restaurant.getAddress());
		r.setPhone(restaurant.getPhone());
		r.setName(restaurant.getName());
		restaurantService.saveRestaurant(r);
		return "redirect:/restaurant/profile";
	}
	
	@RequestMapping("changePassword")
	public String changePass(Principal principal, Model model) {
		userRestaurant(model, principal);
		return "restaurant/changePassword";
	}
	
	@PostMapping("changePassword/{restaurantId}")
	public String changePassword(Model model,
			@PathVariable("restaurantId") Long restaurantId) {
		try {
			String password = request.getParameter("password");
			String passwordEnter = request.getParameter("passwordEnter");
			if(!password.equalsIgnoreCase(passwordEnter)) {
				return "redirect:/restaurant/changePassword?error=true";
			} else {
				Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
				restaurant.setPassword(passwordEncoder.encode(password));
				restaurantService.saveRestaurant(restaurant);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:/restaurant/changePassword?success=true";
	}
	
	@RequestMapping("menu")
	public String menu(Principal principal, Model model) {
		List<Restaurant> restaurants = restaurantService.getAllRestaurants();
		Restaurant restaurant = null;
		if(principal != null) {
			long id = 0l;
			String email = principal.getName();
			for(Restaurant i : restaurants) {
				if(i.getEmail().equalsIgnoreCase(email)) {
					id = i.getRestaurantId();
					break;
				}
			}
			restaurant = restaurantService.getRestaurantById(id);
			model.addAttribute("restaurant", restaurant);
		}
		return "restaurant/menuRestaurant";
	}
	
	@RequestMapping("menu/addProduct")
	public String addProduct(Principal principal, Model model) {
		userRestaurant(model, principal);
		return "restaurant/addProduct";
	}
	
	@PostMapping("menu/addNewProduct")
	public String addNewProduct(Principal principal) {
		try {
			List<Restaurant> restaurants = restaurantService.getAllRestaurants();
			Restaurant restaurant = null;
			if(principal != null) {
				long id = 0l;
				String email = principal.getName();
				for(Restaurant i : restaurants) {
					if(i.getEmail().equalsIgnoreCase(email)) {
						id = i.getRestaurantId();
						break;
					}
				}
				restaurant = restaurantService.getRestaurantById(id);
			}
			String categoryName = request.getParameter("category");
			String productName = request.getParameter("name");
			String description = request.getParameter("description");
			double price = Double.parseDouble(request.getParameter("price"));
			Part part = request.getPart("imageFile");
			String imageFile = Path.of(part.getSubmittedFileName()).getFileName().toString();
			String path = System.getProperty("user.dir");
			path += "/src/main/resources/static/images";
			part.write(path + "/" + imageFile);
			Product product = new Product();
			product.setName(productName);
			product.setDescription(description);
			product.setPrice(price);
			product.setRestaurant(restaurant);
			product.setImage(imageFile);
			product.setStatus(1);
			product.setOrderQuantity(0);
			int ok = 0;
			for(Category category : restaurant.getCategories()) {
				if(category.getName().equalsIgnoreCase(categoryName)) {
					product.setCategory(category);
					ok = 1;
					break;
				} 
			}
			if(ok == 0) {
				Category c = new Category();
				c.setRestaurant(restaurant);
				c.setName(categoryName);
				categoryService.saveCategory(c);
				product.setCategory(c);
			}
			productService.saveProduct(product);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:/restaurant/menu";
	}
	
	@RequestMapping("menu/deleteProduct/{productId}")
	public String deleteProduct(@PathVariable("productId") Long productId) {
		productService.deleteProductById(productId);
		return "redirect:/restaurant/menu";
	}
	
	@GetMapping("menu/updateProduct/{productId}")
	public String updateProduct(@PathVariable("productId") Long productId
			,Principal principal, Model model) {
		userRestaurant(model, principal);
		model.addAttribute("product", productService.getProductById(productId)); 
		return "restaurant/updateProduct"; 
	}
	
	@PostMapping("menu/updateProduct/{productId}")
	public String update(@ModelAttribute Product product,
			@PathVariable("productId") Long productId) {
		Product p = productService.getProductById(productId);
		p.setName(product.getName());
		p.setDescription(product.getDescription());
		p.setPrice(product.getPrice());
		productService.saveProduct(p);
		return "redirect:/restaurant/menu";
	}
	
	@RequestMapping("orderHistory")
	public String orderHistory(Model model,Principal principal) {
		List<Restaurant> restaurants = restaurantService.getAllRestaurants();
		Restaurant restaurant = null;
		if(principal != null) {
			long id = 0l;
			String email = principal.getName();
			for(Restaurant i : restaurants) {
				if(i.getEmail().equalsIgnoreCase(email)) {
					id = i.getRestaurantId();
					break;
				}
			}
			restaurant = restaurantService.getRestaurantById(id);
			model.addAttribute("restaurant", restaurant);
		}
		List<Order> orders = new ArrayList<Order>();
		for(Order order : orderService.getAllOrders()) {
			if((order.getRestaurant().getRestaurantId() == restaurant.getRestaurantId()) &&
					order.getStatus_restaurant() == 1) {
				orders.add(order); 
			}
		}
		List<OrderProduct> orderProducts = orderProductService.getAllOrderProducts();
		Collections.sort(orders, (o1, o2) -> o2.getOrderDateDelivered().compareTo(o1.getOrderDateDelivered()));
		model.addAttribute("orderProducts", orderProducts);
		model.addAttribute("orders", orders);
		return "restaurant/orderHistory";
	}
	
	@GetMapping("orderDelete/{orderId}")
	public String orderDelete(@PathVariable("orderId") Long orderId) {
		List<OrderProduct> orderProducts = orderProductService.getAllOrderProducts();
		for(OrderProduct orderProduct : orderProducts) {
			if(orderProduct.getOrder().getOrderId() == orderId) {
				orderProductService.deleteOrderProductById(orderProduct.getId());
			}
		}
		orderService.deleteOrderById(orderId);
		return "redirect:/restaurant";
	}
}
