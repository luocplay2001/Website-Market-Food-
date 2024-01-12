package com.nguyenkien.mms.controller;

import java.security.Principal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nguyenkien.mms.service.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nguyenkien.mms.model.Customer;
import com.nguyenkien.mms.model.Order;
import com.nguyenkien.mms.model.OrderProduct;
import com.nguyenkien.mms.model.Product;
import com.nguyenkien.mms.model.Restaurant;
import com.nguyenkien.mms.model.Shipper;

@Controller
@RequestMapping("shipper")
public class ShipperController {
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
	private EmailService emailService;
	
	public void userShipper(Model model,Principal principal) {
		List<Shipper> shippers = shipperService.getAllShippers();
		Shipper shipper = null;
		if(principal != null) {
			long id = 0l;
			String email = principal.getName();
			for(Shipper i : shippers) {
				if(i.getEmail().equalsIgnoreCase(email)) {
					id = i.getShipperId();
					break;
				}
			}
			shipper = shipperService.getShipperById(id);
			model.addAttribute("shipper", shipper);
		}
	}
	
	@RequestMapping 
	public String homeShipper(Model model,Principal principal) {
		userShipper(model,principal);
		List<Order> orders = new ArrayList<Order>();
		for(Order order : orderService.getAllOrders()) {
			if(order.getShipper() == null) {
				orders.add(order);
			}
		}
		model.addAttribute("orders", orders);
		return "shipper/homeShipper";
	}
	
	@RequestMapping("profile")
	public String profile(Principal principal, Model model) {
		userShipper(model,principal);
		return "shipper/profileShipper";
	}
	
	@RequestMapping("uploadProfile")
	public String uploadProfile(Principal principal, Model model) {
		userShipper(model,principal);
		return "shipper/uploadProfileShipper";
	}
	
	@PostMapping("uploadProfile/{shipperId}")
	public String upload(@ModelAttribute Shipper shipper) {
		Shipper s = shipperService.getShipperById(shipper.getShipperId());
		s.setAddress(shipper.getAddress());
		s.setPhone(shipper.getPhone());
		s.setName(shipper.getName());
		shipperService.saveShipper(s);
		return "redirect:/shipper/profile";
	}
	
	@RequestMapping("changePassword")
	public String changePass(Principal principal, Model model) {
		userShipper(model,principal);
		return "shipper/changePassword";
	}
	
	@PostMapping("changePassword/{shipperId}")
	public String changePassword(Model model,
			@PathVariable("shipperId") Long shipperId) {
		try {
			String password = request.getParameter("password");
			String passwordEnter = request.getParameter("passwordEnter");
			if(!password.equalsIgnoreCase(passwordEnter)) {
				return "redirect:/shipper/changePassword?error=true";
			} else {
				Shipper shipper = shipperService.getShipperById(shipperId);
				shipper.setPassword(passwordEncoder.encode(password));
				shipperService.saveShipper(shipper);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:/shipper/changePassword?success=true";
	}
	
	@RequestMapping("orderDid/{orderId}")
	public String orderDid(Model model,Principal principal,
			@PathVariable("orderId") Long orderId) {
		List<Shipper> shippers = shipperService.getAllShippers();
		Shipper shipper = null;
		if(principal != null) {
			long id = 0l;
			String email = principal.getName();
			for(Shipper i : shippers) {
				if(i.getEmail().equalsIgnoreCase(email)) {
					id = i.getShipperId();
					break;
				}
			}
			shipper = shipperService.getShipperById(id);
			model.addAttribute("shipper", shipper);
		}
		Order order = orderService.getOrderById(orderId);
		order.setShipper(shipper);
		orderService.saveOrder(order);
		return "redirect:/shipper/orderDid";
	}
	
	@RequestMapping("orderNotReceived/{orderId}")
	public String orderNotReceived(Model model,Principal principal,
			@PathVariable("orderId") Long orderId) {
		List<Shipper> shippers = shipperService.getAllShippers();
		Shipper shipper = null;
		if(principal != null) {
			long id = 0l;
			String email = principal.getName();
			for(Shipper i : shippers) {
				if(i.getEmail().equalsIgnoreCase(email)) {
					id = i.getShipperId();
					break;
				}
			}
			shipper = shipperService.getShipperById(id);
			model.addAttribute("shipper", shipper);
		}
		Order order = orderService.getOrderById(orderId);
		order.setShipper(null);
		orderService.saveOrder(order);
		return "redirect:/shipper/orderDid";
	}
	
	@RequestMapping("orderDid")
	public String orderDid(Model model,Principal principal) {
		List<Shipper> shippers = shipperService.getAllShippers();
		Shipper shipper = null;
		if(principal != null) {
			long id = 0l;
			String email = principal.getName();
			for(Shipper i : shippers) {
				if(i.getEmail().equalsIgnoreCase(email)) {
					id = i.getShipperId();
					break;
				}
			}
			shipper = shipperService.getShipperById(id);
			model.addAttribute("shipper", shipper);
		}
		List<OrderProduct> orderProducts = orderProductService.getAllOrderProducts();
		model.addAttribute("orderProducts", orderProducts);
		List<Order> orders = new ArrayList<Order>();
		for(Order o : orderService.getAllOrders()) {
			if(o.getShipper() != null 
					&& (o.getShipper().getShipperId() 
							== shipper.getShipperId()) 
					&& o.getStatus_shipper() == 0) {
				orders.add(o);
			}
		}
		model.addAttribute("orders", orders);
		return "shipper/orderDid";
	}
	
	@RequestMapping("delivere/{orderId}")
	public String orderDelivere(Model model,Principal principal,
			@PathVariable("orderId") Long orderId) {
		userShipper(model,principal);
		Order order = orderService.getOrderById(orderId);
		order.setStatus_shipper(-1);
		order.setStatus_restaurant(1);
		orderService.saveOrder(order);
		List<OrderProduct> orderProducts = orderProductService.getAllOrderProducts();
		for(OrderProduct orderProduct: orderProducts) {
			if(orderProduct.getOrder().getOrderId() == order.getOrderId()) {
				Product product = orderProduct.getProduct();
				Product p = orderProduct.getProduct();
				product.setOrderQuantity(p.getOrderQuantity() + orderProduct.getQuantity());
				productService.saveProduct(product);
			}
		}
		
		return "redirect:/shipper/delivere";
	}
	
	@RequestMapping("delivere")
	public String delivere(Model model,Principal principal) {
		List<Shipper> shippers = shipperService.getAllShippers();
		Shipper shipper = null;
		if(principal != null) {
			long id = 0l;
			String email = principal.getName();
			for(Shipper i : shippers) {
				if(i.getEmail().equalsIgnoreCase(email)) {
					id = i.getShipperId();
					break;
				}
			}
			shipper = shipperService.getShipperById(id);
			model.addAttribute("shipper", shipper);
		}
		
//		Order order = orderService.getOrderById(orderId);
//		order.setShipper(shipper);
//		orderService.saveOrder(order);
		List<OrderProduct> orderProducts = orderProductService.getAllOrderProducts();
		model.addAttribute("orderProducts", orderProducts);
		List<Order> orders = new ArrayList<Order>();
		for(Order o : orderService.getAllOrders()) {
			if(o.getShipper() != null 
					&& (o.getShipper().getShipperId() 
							== shipper.getShipperId()) 
					&& o.getStatus_shipper() == -1) {
				orders.add(o);
			}
		}
		model.addAttribute("orders", orders);
		return "shipper/delivere";
	}
	
	@RequestMapping("orderDelivered/{orderId}")
	public String orderDelivered(Model model,Principal principal,
			@PathVariable("orderId") Long orderId) {
		userShipper(model,principal);
		Order order = orderService.getOrderById(orderId);
		order.setStatus_shipper(1);
		order.setStatus_customer(1);
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");
		String strDate = formatter.format(date);
		order.setOrderDateDelivered(strDate);
		orderService.saveOrder(order);
		return "redirect:/shipper/orderDelivered";
	}
	
	@RequestMapping("orderDelivered")
	public String orderDelivered(Model model,Principal principal) {
		List<Shipper> shippers = shipperService.getAllShippers();
		Shipper shipper = null;
		if(principal != null) {
			long id = 0l;
			String email = principal.getName();
			for(Shipper i : shippers) {
				if(i.getEmail().equalsIgnoreCase(email)) {
					id = i.getShipperId();
					break;
				}
			}
			shipper = shipperService.getShipperById(id);
			model.addAttribute("shipper", shipper);
		}
		List<OrderProduct> orderProducts = orderProductService.getAllOrderProducts();
		model.addAttribute("orderProducts", orderProducts);
		List<Order> orders = new ArrayList<Order>();
		for(Order o : orderService.getAllOrders()) {
			if(o.getShipper() != null 
					&& (o.getShipper().getShipperId() 
							== shipper.getShipperId()) 
					&& o.getStatus_shipper() == 1) {
				orders.add(o);
			}
		}
		Collections.sort(orders, (o1,o2) -> o2.getOrderDateDelivered().compareTo(o1.getOrderDateDelivered()));
		model.addAttribute("orders", orders);
		return "shipper/orderDelivered";
	}
}
