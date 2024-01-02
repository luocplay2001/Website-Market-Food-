package com.nguyenkien.mms.controller;

import java.nio.file.Path;
import java.security.Principal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.nguyenkien.mms.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nguyenkien.mms.model.Customer;
import com.nguyenkien.mms.model.CustomerRole;
import com.nguyenkien.mms.model.Product;
import com.nguyenkien.mms.model.Restaurant;
import com.nguyenkien.mms.model.RestaurantRole;
import com.nguyenkien.mms.model.Role;
import com.nguyenkien.mms.model.Shipper;
import com.nguyenkien.mms.model.ShipperRole;
import com.nguyenkien.mms.service.CustomerRoleService;
import com.nguyenkien.mms.service.CustomerService;
import com.nguyenkien.mms.service.DiscountService;
import com.nguyenkien.mms.service.ProductService;
import com.nguyenkien.mms.service.RestaurantRoleService;
import com.nguyenkien.mms.service.RestaurantService;
import com.nguyenkien.mms.service.RoleService;
import com.nguyenkien.mms.service.ShipperRoleService;
import com.nguyenkien.mms.service.ShipperService;
import com.nguyenkien.mms.utils.WebUtils;

@Controller
public class HomeController {
	
	@Autowired
	RestaurantService restaurantService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	ShipperService shipperService;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	CustomerRoleService customerRoleService;
	
	@Autowired
	RestaurantRoleService restaurantRoleService;
	
	@Autowired
	ShipperRoleService shipperRoleService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	DiscountService discountService;
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/")
	public String home(Model model, Principal principal) {
		List<Restaurant> restaurants = restaurantService.getAllRestaurants();
		List<Customer> customers = customerService.getAllCustomers();
		List<Shipper> shippers = shipperService.getAllShippers();
		if(principal != null) {
			String email = principal.getName();
			 

			User loginedCustomer = (User) ((Authentication) principal).getPrincipal();		 
			String customer = WebUtils.toString(loginedCustomer);
			
			
			for(Customer i : customers) {
				if(i.getEmail().equalsIgnoreCase(email)) {
					return "redirect:customer";
				}
			}
			
			for(Restaurant i : restaurants) {
				if(i.getEmail().equalsIgnoreCase(email)) {
					return "redirect:restaurant";
				}
			}
			
			for(Shipper i : shippers) {
				if(i.getEmail().equalsIgnoreCase(email)) {
					return "redirect:shipper";
				}
			}
		}
		model.addAttribute("restaurants",restaurants);
			
		return "home";
	}
	
	@RequestMapping(value = "/logoutSuccessful")
	public String logoutSuccessfulPage(Model model) {
//		model.addAttribute("title", "Logout");
		return "redirect:/login";
	}
	
	
	@RequestMapping("/admin")
	public String adminPage(Model model, Principal principal) {
		
		User loginedCustomer = (User) ((Authentication) principal).getPrincipal();
		String customer = WebUtils.toString(loginedCustomer);
		model.addAttribute("customer", customer);
		
		return "admin";
	}
	
	@RequestMapping("/view-restaurant/{id}") 
	public String viewRestaurant(Model model,@PathVariable("id") Long id) {
		Restaurant restaurant = restaurantService.getRestaurantById(id);
		model.addAttribute("restaurant", restaurant);
		return "viewRestaurant";
	}
		
	@RequestMapping("/403")
	public String accessDenied(Model model, Principal principal) {

		if (principal != null) {
			User loginedCustomer = (User) ((Authentication) principal).getPrincipal();

			String customer = WebUtils.toString(loginedCustomer);
			model.addAttribute("customer", customer);

			String message = "Hi " + principal.getName() //
					+ "<br> You do not have permission to access this page!";
			model.addAttribute("message", message);

		}

		return "403Page";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/register")
	public String register() {
		
		return "register";
	}
	
	@RequestMapping("products")
	public String products(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "products";
	}
	
	@PostMapping("/register")
	public String registered(Model model) {
		try {
			String name = request.getParameter("name");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String passwordEnter = request.getParameter("passwordEnter");
			String person = request.getParameter("person");
			String gender = request.getParameter("gender");
			Part part = request.getPart("imageFile");
			String imageFile = Path.of(part.getSubmittedFileName()).getFileName().toString();
			String path = System.getProperty("user.dir");
			path += "/src/main/resources/static/images";
			part.write(path + "/" + imageFile);
			String ms = "";
			if(!password.equalsIgnoreCase(passwordEnter) || username.equalsIgnoreCase("")) {
				ms = "Thông tin không hợp lệ!";
			}
			if(ms.equals("")) {
				if(person.equals("customer")) {
					int ok = 0;
					for(Customer customer: customerService.getAllCustomers()) {
						if(customer.getEmail().equalsIgnoreCase(username)) {
							ok = 1;
							ms = "Tài khoản đã tồn tại!";
							break;
						}
					}
					if(ok == 0) {
						Customer customer = new Customer();
						customer.setName(name);
						customer.setEmail(username);
						customer.setPassword(passwordEncoder.encode(password));
						Date date = new Date(System.currentTimeMillis());
						SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");
						String strDate = formatter.format(date);
						customer.setRegisterDate(strDate);
						customer.setDiscount(discountService.getDiscountById(1l));
						if(gender.equals("nu")) {
							customer.setGender(0);
						} else {
							customer.setGender(1);
						}
						customer.setImage(imageFile);
						customerService.saveCustomer(customer);
						CustomerRole customerRole = new CustomerRole();
						customerRole.setCustomer(customerService.getCustomerById(customer.getCustomerId()));
						for(Role role : roleService.getAllRoles()) {
							if(role.getRoleName().equals("ROLE_CUSTOMER")) {
								customerRole.setRole(role);
								break;
							}
						}
						customerRoleService.saveCustomerRole(customerRole);
					}
				} 
				
				if(person.equals("restaurant")) {
					int ok = 0;
					for(Restaurant restaurant: restaurantService.getAllRestaurants()) {
						if(restaurant.getEmail().equalsIgnoreCase(username)) {
							ok = 1;
							ms = "Tài khoản đã tồn tại!";
							break;
						}
					}
					if(ok == 0) {
						Restaurant restaurant = new Restaurant();
						restaurant.setName(name);
						restaurant.setEmail(username);
						restaurant.setPassword(passwordEncoder.encode(password));
						Date date = new Date(System.currentTimeMillis());
						SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");
						String strDate = formatter.format(date);
						restaurant.setRegisterDate(strDate);
						if(gender.equals("nu")) {
							restaurant.setGender(0);
						} else {
							restaurant.setGender(1);
						}
						restaurant.setImage(imageFile);
						restaurantService.saveRestaurant(restaurant);
						RestaurantRole restaurantRole = new RestaurantRole();
						restaurantRole.setRestaurant(restaurantService.getRestaurantById(restaurant.getRestaurantId()));
						for(Role role : roleService.getAllRoles()) {
							if(role.getRoleName().equals("ROLE_RESTAURANT")) {
								restaurantRole.setRole(role);
								break;
							}
						}
						restaurantRoleService.saveRestaurantRole(restaurantRole);
					}
				}
				
				if(person.equals("shipper")) {
					int ok = 0;
					for(Shipper shipper: shipperService.getAllShippers()) {
						if(shipper.getEmail().equalsIgnoreCase(username)) {
							ok = 1;
							ms = "Tài khoản đã tồn tại!";
							break;
						}
					}
					if(ok == 0) {
						Shipper shipper =  new Shipper();
						shipper.setName(name);
						shipper.setEmail(username);
						shipper.setPassword(passwordEncoder.encode(password));
						Date date = new Date(System.currentTimeMillis());
						SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");
						String strDate = formatter.format(date);
						shipper.setRegisterDate(strDate);
						if(gender.equals("nu")) {
							shipper.setGender(0);
						} else {
							shipper.setGender(1);
						}
						shipper.setImage(imageFile);
						shipperService.saveShipper(shipper);
						ShipperRole shipperRole = new ShipperRole();
						shipperRole.setShipper(shipperService.getShipperById(shipper.getShipperId()));
						for(Role role : roleService.getAllRoles()) {
							if(role.getRoleName().equals("ROLE_SHIPPER")) {
								shipperRole.setRole(role);
								break;
							}
						}
						shipperRoleService.saveShipperRole(shipperRole);
					}
				}
			}
			model.addAttribute("ms", ms);
		} catch (Exception e) {
			
		}
		return "register";
	}
	
	@GetMapping("products")
	public String products(Model model,Principal principal) {
		List<Product> products = productService.getAllProducts();
		for (int i = 0; i < products.size(); i++) {
			products.get(i).setPriceVND(NumberUtils.getVND(products.get(i).getPrice()));
		}
		model.addAttribute("products", products);
		return "products";
	}
	
	@GetMapping("productBestSeller") 
	public String productBestSeller(Model model, Principal principal) {
		List<Product> products = productService.getAllProducts();
		for (int i = 0; i < products.size(); i++) {
			products.get(i).setPriceVND(NumberUtils.getVND(products.get(i).getPrice()));
		}
//		System.out.println(NumberUtils.getVND(products.get(0).getPrice()));
		products.sort((p1, p2) -> p2.getOrderQuantity() - p1.getOrderQuantity()); 
		model.addAttribute("products", products);
		return "products";
	}
	
	@GetMapping("productsPriceLowToHigh") 
	public String productsPriceLowToHigh(Model model, Principal principal) {
		List<Product> products = productService.getAllProducts();
		for (int i = 0; i < products.size(); i++) {
			products.get(i).setPriceVND(NumberUtils.getVND(products.get(i).getPrice()));
		}
		Collections.sort(products,new Comparator<Product>() {
			 // p1.getPrice() < p2.getPrice()
            public int compare(Product p1, Product p2) {
                if(p1.getPrice() >= p2.getPrice())
                	return 1;
                return -1;
            }
		});
		model.addAttribute("products", products);
		return "products";
	}
	
	@GetMapping("productsPriceHighToLow") 
	public String productsPriceHighToLow(Model model, Principal principal) {
		List<Product> products = productService.getAllProducts();
		for (int i = 0; i < products.size(); i++) {
			products.get(i).setPriceVND(NumberUtils.getVND(products.get(i).getPrice()));
		}
		Collections.sort(products,new Comparator<Product>() {
			 // p1.getPrice() < p2.getPrice()
            public int compare(Product p1, Product p2) {
                if(p1.getPrice() <= p2.getPrice())
                	return 1;
                return -1;  
            }
		});
		model.addAttribute("products", products);
		return "products";
	}
	
	
	
	@PostMapping("products")
	public String productList(Model model,Principal principal) {
		try {
			String search = request.getParameter("search").toLowerCase();
			Set<Product> products = new HashSet<Product>();
			for(Product product : productService.getAllProducts()) {
				for(String i : search.split(" ")) {
					if(product.getName().toLowerCase().contains(i)) {
						products.add(product);
					}
				}
			}
			Iterator<Product> iterator = products.iterator();
			List<Product> list = new ArrayList<>();
			while (iterator.hasNext()) {
				Product product = iterator.next();
				product.setPriceVND(NumberUtils.getVND(product.getPrice()));
				list.add(product);
			}
			model.addAttribute("products", list);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "products";
	}
}
