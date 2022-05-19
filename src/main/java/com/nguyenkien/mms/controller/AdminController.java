package com.nguyenkien.mms.controller;

import java.nio.file.Path;
import java.security.Principal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nguyenkien.mms.model.Customer;
import com.nguyenkien.mms.model.CustomerRole;
import com.nguyenkien.mms.model.Restaurant;
import com.nguyenkien.mms.model.RestaurantRole;
import com.nguyenkien.mms.model.Role;
import com.nguyenkien.mms.model.Shipper;
import com.nguyenkien.mms.model.ShipperRole;
import com.nguyenkien.mms.service.CustomerRoleService;
import com.nguyenkien.mms.service.CustomerService;
import com.nguyenkien.mms.service.DiscountService;
import com.nguyenkien.mms.service.OrderProductService;
import com.nguyenkien.mms.service.OrderService;
import com.nguyenkien.mms.service.ProductService;
import com.nguyenkien.mms.service.RestaurantRoleService;
import com.nguyenkien.mms.service.RestaurantService;
import com.nguyenkien.mms.service.RoleService;
import com.nguyenkien.mms.service.ShipperRoleService;
import com.nguyenkien.mms.service.ShipperService;
import com.nguyenkien.mms.service.ShoppingCartService;


@Controller
@RequestMapping("/admin")
public class AdminController {
	
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
	DiscountService discountService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	CustomerRoleService customerRoleService;
	
	@Autowired
	ShipperRoleService shipperRoleService;
	
	@Autowired
	RestaurantRoleService restaurantRoleService;
	
	@GetMapping
	public String home(Model model, Principal principal) {
		List<Customer> customers = customerService.getAllCustomers();
		model.addAttribute("customers", customers);
		return "admin/adminHome";
	}
	
	@RequestMapping("listRestaurant")
	public String listRestaurant(Model model, Principal principal) {
		List<Restaurant> restaurants = restaurantService.getAllRestaurants();
		model.addAttribute("restaurants", restaurants);
		return "admin/listRestaurant";
	}
	
	@RequestMapping("listShipper")
	public String listShipper(Model model, Principal principal) {
		List<Shipper> shippers = shipperService.getAllShippers();
		model.addAttribute("shippers", shippers);
		return "admin/listShipper";
	}
	
	@GetMapping("addUser")
	public String addUser() {		
		return "admin/addUser";
	}
	
	@RequestMapping("deleteCustomer/{customerId}")
	public String delleteCustomer(@PathVariable("customerId") Long customerId) {
		Customer customer = customerService.getCustomerById(customerId);
		customer.setDiscount(null);  
		List<CustomerRole> customerRoles = customerRoleService.getAllCustomerRoles();
		for(CustomerRole customerRole : customerRoles) {
			if(customerRole.getCustomer().getCustomerId() == customerId) {
				customerRoleService.deleteCustomerRoleById(customerRole.getId());
			}
		}
		customerService.deleteCustomerById(customerId);
		return "redirect:/admin";
	}
	
	@GetMapping("updateCustomer/{customerId}") 
	public String updateCustomer(Model model,
			@PathVariable("customerId") Long customerId) {
		model.addAttribute("customer", customerService.getCustomerById(customerId));
		return "admin/updateCustomer";
	}
	
	@PostMapping("updateCustomer/{customerId}") 
	public String updateNewCustomer(@PathVariable("customerId") Long customerId) {
		try {
			String name = request.getParameter("name");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			String address = request.getParameter("address");
			String phone = request.getParameter("phone");
			Customer customer = customerService.getCustomerById(customerId);
			customer.setName(name);
			customer.setEmail(username);
			if(!password.isEmpty()) {
				customer.setPassword(passwordEncoder.encode(password));

			}
			customer.setAddress(address);
			customer.setPhone(phone);		
			if(gender.equals("nu")) {
				customer.setGender(0);
			} else {
				customer.setGender(1);
			}
			customerService.saveCustomer(customer);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "redirect:/admin";
	}
	
	
	
	@RequestMapping("deleteShipper/{shipperId}")
	public String delleteShipper(@PathVariable("shipperId") Long shipperId) {
		List<ShipperRole> shipperRoles = shipperRoleService.getAllShipperRoles();
		for(ShipperRole shipperRole : shipperRoles) {
			if(shipperRole.getShipper().getShipperId() == shipperId) {
				shipperRoleService.deleteShipperRoleById(shipperRole.getId());
			}
		}
		shipperService.deleteShipperById(shipperId);
		return "redirect:/admin/listShipper";
	}
	
	@GetMapping("updateShipper/{shipperId}") 
	public String updateShipper(Model model,
			@PathVariable("shipperId") Long shipperId) {
		model.addAttribute("shipper", shipperService.getShipperById(shipperId));
		return "admin/updateShipper";
	}
	
	@PostMapping("updateShipper/{shipperId}") 
	public String updateNewShipper(@PathVariable("shipperId") Long shipperId) {
		try {
			String name = request.getParameter("name");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			String address = request.getParameter("address");
			String phone = request.getParameter("phone");
			Shipper shipper = shipperService.getShipperById(shipperId);
			shipper.setName(name);
			shipper.setEmail(username);
			if(!password.isEmpty()) {
				shipper.setPassword(passwordEncoder.encode(password));

			}
			shipper.setAddress(address);
			shipper.setPhone(phone);
			if(gender.equals("nu")) {
				shipper.setGender(0);
			} else {
				shipper.setGender(1);
			}
			shipperService.saveShipper(shipper);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "redirect:/admin/listShipper";
	}
	
	@RequestMapping("deleteRestaurant/{restaurantId}")
	public String delleteRestaurant(@PathVariable("restaurantId") Long restaurantId) {
		List<RestaurantRole> restaurantRoles = restaurantRoleService.getAllRestaurantRoles();
		for(RestaurantRole restaurantRole : restaurantRoles) {
			if(restaurantRole.getRestaurant().getRestaurantId() == restaurantId) {
				restaurantRoleService.deleteRestaurantRoleById(restaurantRole.getId());
			}
		}
		restaurantService.deleteRestaurantById(restaurantId);
		return "redirect:/admin/listRestaurant";
	}
	
	
	@GetMapping("updateRestaurant/{restaurantId}") 
	public String updateRestaurant(Model model,
			@PathVariable("restaurantId") Long restaurantId) {
		model.addAttribute("restaurant", restaurantService.getRestaurantById(restaurantId));
		return "admin/updateRestaurant";
	}
	
	@PostMapping("updateRestaurant/{restaurantId}") 
	public String updateNewRestaurant(@PathVariable("restaurantId") Long restaurantId) {
		try {
			System.out.println(0);
			String name = request.getParameter("name");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			String address = request.getParameter("address");
			String phone = request.getParameter("phone");
			Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
			restaurant.setName(name);
			restaurant.setEmail(username);
			if(!password.isEmpty()) {
				restaurant.setPassword(passwordEncoder.encode(password));
			}
			restaurant.setAddress(address);
			restaurant.setPhone(phone);		
			if(gender.equals("nu")) {
				restaurant.setGender(0);
			} else {
				restaurant.setGender(1);
			}
			System.out.println("Restaurant: " + restaurant);
			restaurantService.saveRestaurant(restaurant);
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println(1);
		return "redirect:/admin/listRestaurant";
	}
	
	
	@PostMapping("addUser")
	public String addNewUser() {
		try {
			String name = request.getParameter("name");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String person = request.getParameter("person");
			String gender = request.getParameter("gender");
			String address = request.getParameter("address");
			String phone = request.getParameter("phone");
			Part part = request.getPart("imageFile");
			String imageFile = Path.of(part.getSubmittedFileName()).getFileName().toString();
			String path = System.getProperty("user.dir");
			path += "/src/main/resources/static/images";
			String ms ="";
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
					part.write(path + "/" + imageFile);
					Customer customer = new Customer();
					customer.setName(name);
					customer.setEmail(username);
					customer.setPassword(passwordEncoder.encode(password));
					customer.setAddress(address);
					customer.setPhone(phone);
					Date date = new Date(System.currentTimeMillis());
					SimpleDateFormat formatter = new SimpleDateFormat("hh:mm dd/MM/yyyy");
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
					part.write(path + "/" + imageFile);
					Restaurant restaurant = new Restaurant();
					restaurant.setName(name);
					restaurant.setEmail(username);
					restaurant.setPassword(passwordEncoder.encode(password));
					restaurant.setAddress(address);
					restaurant.setPhone(phone);
					Date date = new Date(System.currentTimeMillis());
					SimpleDateFormat formatter = new SimpleDateFormat("hh:mm dd/MM/yyyy");
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
					return "redirect:/admin/listRestaurant";
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
					part.write(path + "/" + imageFile);
					Shipper shipper =  new Shipper();
					shipper.setName(name);
					shipper.setEmail(username);
					shipper.setPassword(passwordEncoder.encode(password));
					shipper.setAddress(address);
					shipper.setPhone(phone);
					Date date = new Date(System.currentTimeMillis());
					SimpleDateFormat formatter = new SimpleDateFormat("hh:mm dd/MM/yyyy");
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
					return "redirect:/admin/listShipper";
				}
			}
			
			if(!ms.equals("")) {
				return "redirect:/admin/addUser?error=true";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "redirect:/admin";
	}
}
