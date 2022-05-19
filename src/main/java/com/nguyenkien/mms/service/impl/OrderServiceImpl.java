package com.nguyenkien.mms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nguyenkien.mms.model.Order;
import com.nguyenkien.mms.repository.OrderRepository;
import com.nguyenkien.mms.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRepository; 
	
	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}
	
	@Override
	public Order getOrderById(Long id) {
		return orderRepository.findById(id).get();
	}
	
	@Override
	public Order saveOrder(Order order) {
		return orderRepository.save(order);
	}
	
	@Override
	public void deleteOrderById(Long id) {
		orderRepository.deleteById(id);	
	}
	
	@Override
	public Order updateOrder(Order order) {
		return orderRepository.save(order);
	}
}
