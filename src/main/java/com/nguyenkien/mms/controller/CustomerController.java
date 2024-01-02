package com.nguyenkien.mms.controller;



import java.security.Principal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.nguyenkien.mms.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nguyenkien.mms.model.CartItem;
import com.nguyenkien.mms.model.Customer;
import com.nguyenkien.mms.model.Order;
import com.nguyenkien.mms.model.OrderProduct;
import com.nguyenkien.mms.model.Product;
import com.nguyenkien.mms.model.Restaurant;
import com.nguyenkien.mms.service.CustomerService;
import com.nguyenkien.mms.service.OrderProductService;
import com.nguyenkien.mms.service.OrderService;
import com.nguyenkien.mms.service.ProductService;
import com.nguyenkien.mms.service.RestaurantService;
import com.nguyenkien.mms.service.ShipperService;
import com.nguyenkien.mms.service.ShoppingCartService;


@Controller
@RequestMapping("/customer")
public class CustomerController {	
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
	
	public void userCustomer(Model model,Principal principal) {
		List<Customer> customers = customerService.getAllCustomers();
		if(principal != null) {
			String email = principal.getName();
			long id = 0;
			for(Customer i : customers) {
				if(i.getEmail().equalsIgnoreCase(email)) {
					id = i.getCustomerId();
					break;
				}
			}
			Customer customer = customerService.getCustomerById(id);
			model.addAttribute("customer", customer);
		}
	}
	
	@RequestMapping
	public String homeCustomer(Model model,Principal principal){
		List<Restaurant> restaurants = restaurantService.getAllRestaurants();
		userCustomer(model, principal);
		model.addAttribute("restaurants",restaurants);
		return "customer/homeCustomer";
	}
	
	
	@GetMapping("products")
	public String products(Model model,Principal principal) {
		userCustomer(model, principal);
		List<Product> products = productService.getAllProducts();
		for (int i = 0; i < products.size(); i++) {
			products.get(i).setPriceVND(NumberUtils.getVND(products.get(i).getPrice()));
		}
		model.addAttribute("products", products);
		return "customer/productsCustomer";
	}
	
	@GetMapping("productBestSeller") 
	public String productBestSeller(Model model, Principal principal) {
		userCustomer(model, principal);
		List<Product> products = productService.getAllProducts();
		for (int i = 0; i < products.size(); i++) {
			products.get(i).setPriceVND(NumberUtils.getVND(products.get(i).getPrice()));
		}
		products.sort((p1, p2) -> p2.getOrderQuantity() - p1.getOrderQuantity());
		model.addAttribute("products", products);
		return "customer/productsCustomer";
	}
	
	@GetMapping("productsPriceLowToHigh") 
	public String productsPriceLowToHigh(Model model, Principal principal) {
		userCustomer(model, principal);
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
		return "customer/productsCustomer";
	}
	
	@GetMapping("productsPriceHighToLow") 
	public String productsPriceHighToLow(Model model, Principal principal) {
		userCustomer(model, principal);
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
		return "customer/productsCustomer";
	}
	
	
	
	@PostMapping("products")
	public String productList(Model model,Principal principal) {
		userCustomer(model, principal);
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
//			for (int i = 0; i < products.size(); i++) {
//				products.get(i).setPriceVND(NumberUtils.getVND(products.get(i).getPrice()));
//			}
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
		return "customer/productsCustomer";
	}
	
	@RequestMapping("profile")
	public String profile(Principal principal, Model model) {
		userCustomer(model, principal);
		return "customer/profileCustomer";
	}
	
	@RequestMapping("uploadProfile")
	public String uploadProfile(Principal principal, Model model) {
		userCustomer(model, principal);
		return "customer/uploadProfileCustomer";
	}
	
	@PostMapping("uploadProfile/{customerId}")
	public String upload(@ModelAttribute Customer customer) {
		Customer c = customerService.getCustomerById(customer.getCustomerId());
		c.setAddress(customer.getAddress());
		c.setPhone(customer.getPhone());
		c.setName(customer.getName());
		customerService.saveCustomer(c);
		return "redirect:/customer/profile";
	}
	
	@RequestMapping("changePassword")
	public String changePass(Principal principal, Model model) {
		userCustomer(model, principal);
		return "customer/changePassword";
	}
	
	@PostMapping("changePassword/{customerId}")
	public String changePassword(Model model,
			@PathVariable("customerId") Long customerId) {
		try {
			String password = request.getParameter("password");
			String passwordEnter = request.getParameter("passwordEnter");
			if(!password.equalsIgnoreCase(passwordEnter)) {
				return "redirect:/customer/changePassword?error=true";
			} else {
				Customer customer = customerService.getCustomerById(customerId);
				customer.setPassword(passwordEncoder.encode(password));
				customerService.saveCustomer(customer);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:/customer/changePassword?success=true";
	}
	
	
	@RequestMapping("view-restaurant/{id}") 
	public String viewRestaurant(Model model,@PathVariable("id") Long id,
			Principal principal) {
		userCustomer(model, principal);
		Restaurant restaurant = restaurantService.getRestaurantById(id);
		model.addAttribute("restaurant", restaurant);
		return "customer/viewRestaurant";
	}
	
	@GetMapping("/shoppingCart/add/{id}")
	public String add(@PathVariable("id") Long productId) {
		Product product = productService.getProductById(productId);
		Collection<CartItem> cartItems = shoppingCartService.getCartItem();
		if(!cartItems.isEmpty()) {
			Product p = null;
			for(CartItem cartItem : cartItems) {
				p = productService.getProductById(cartItem.getProductId());
			}
			if(product.getRestaurant().getRestaurantId()
					!= p.getRestaurant().getRestaurantId()) {
				shoppingCartService.clear();
			}
		}
		if(product != null) {
			CartItem item = new CartItem();
			item.setProductId(product.getProductId());
			item.setName(product.getName());
			item.setDescription(product.getDescription());
			item.setImage(product.getImage());
			item.setPrice(product.getPrice());
			item.setQuantity(1);
			shoppingCartService.add(item);
		}
		
		return "redirect:/customer/shoppingCart/list";
	}
	
	@GetMapping("shoppingCart/list")
	public String list(Model model, Principal principal) {
		Collection<CartItem> cartItems = shoppingCartService.getCartItem();
		userCustomer(model, principal);
		model.addAttribute("cartItems",cartItems);
		model.addAttribute("total",shoppingCartService.getAmount());
		model.addAttribute("count", shoppingCartService.getCount());
		if(!cartItems.isEmpty()) {
			Iterator<CartItem> itr = cartItems.iterator();
			Product product = productService.getProductById(itr.next().getProductId());
			Restaurant restaurant = product.getRestaurant();
			model.addAttribute("restaurant", restaurant);
		}
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");
		String strDate = formatter.format(date);
		model.addAttribute("date", strDate);
		return "customer/shoppingCart";
	}
	
	@GetMapping("remove/{productId}")
	public String remove(@PathVariable("productId") Long productId) {
		shoppingCartService.remove(productId);
		return "redirect:/customer/shoppingCart/list";
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
		return "redirect:/customer/orderDetails";
	}
	
	@RequestMapping("/orderDetails")
	public String orderDetails(Principal principal, Model model) {  
		Collection<CartItem> cartItems = shoppingCartService.getCartItem();
		List<Order> orders = orderService.getAllOrders();
		if(cartItems.isEmpty() && orders.isEmpty()) {
			return "redirect:/customer/shoppingCart/list";
		}
		Order order = new Order();
		order.setPrice(shoppingCartService.getAmount());
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");
		String strDate = formatter.format(date);
		order.setOrderDate(strDate);
		order.setQuantity(shoppingCartService.getCount()); 
		List<Customer> customers = customerService.getAllCustomers();
		if(principal != null) { 
			Long id = 0l;
			String email = principal.getName();
			for(Customer i : customers) {
				if(i.getEmail().equalsIgnoreCase(email)) {
					id = i.getCustomerId();
					break; 
				}
			}
			Customer customer = customerService.getCustomerById(id);
			model.addAttribute("customer", customer);
			order.setCustomer(customer);
		}
		if(!cartItems.isEmpty()) {
			for(CartItem cartItem: cartItems) {
				Product product = productService.getProductById(cartItem.getProductId());
				order.setRestaurant(product.getRestaurant());
			}
			order.setStatus_customer(0);
			orderService.saveOrder(order);
			List<Order> orderss = orderService.getAllOrders();
			for(CartItem cartItem : cartItems) {
				OrderProduct orderProduct = new OrderProduct();
				Product product = new Product();
				convertCartItemToSet(product, cartItem);// 4 /
				orderProduct.setOrder(orderss.get(orderss.size() - 1));
				orderProduct.setProduct(product);
				orderProduct.setQuantity(cartItem.getQuantity());
				orderProductService.saveOrderProduct(orderProduct);
			}
			List<OrderProduct> orderProducts = orderProductService.getAllOrderProducts();
		}
		shoppingCartService.clear();
		List<OrderProduct> orderProducts = orderProductService.getAllOrderProducts();
		model.addAttribute("orderProducts", orderProducts);
		return "customer/orderDetails";
	}
	
	
	
	public void convertCartItemToSet(Product product, CartItem cartItem) {
		product.setProductId(cartItem.getProductId());
		product.setDescription(cartItem.getDescription());
		product.setImage(cartItem.getImage());
		product.setName(cartItem.getName());
		product.setPrice(cartItem.getPrice());
		product.setStatus(1);
		Product p = productService.getProductById(product.getProductId());
		product.setRestaurant(p.getRestaurant());
		product.setCategory(p.getCategory());
	}
	
	@PostMapping("orderDetails/{orderId}")
	public String feedback(@PathVariable("orderId") Long orderId) {
		try {
			String feedback_shipper = request.getParameter("feedbackShipper");
			String feedback_restaurant = request.getParameter("feedbackRestaurant");
			Order order = orderService.getOrderById(orderId);
			order.setFeedback_shipper(feedback_shipper);
			order.setFeedback_restaurant(feedback_restaurant);
			orderService.saveOrder(order);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:/customer/orderDetails";
	}
}
