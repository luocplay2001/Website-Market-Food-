package com.nguyenkien.mms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nguyenkien.mms.model.OrderProduct;
import com.nguyenkien.mms.repository.OrderProductRepository;
import com.nguyenkien.mms.service.OrderProductService;

@Service
public class OrderProductServiceImpl implements OrderProductService{
	@Autowired
	private OrderProductRepository orderProductRepository;
		
	@Override
	public List<OrderProduct> getAllOrderProducts() {
		return orderProductRepository.findAll();
	}
	
	@Override
	public OrderProduct getOrderProductById(Long id) {
		return orderProductRepository.findById(id).get();
	}
	
	@Override
	public OrderProduct saveOrderProduct(OrderProduct orderProduct) {
		return orderProductRepository.save(orderProduct);
	}
	
	@Override
	public void deleteOrderProductById(Long id) {
		orderProductRepository.deleteById(id);	
	}
	
	@Override
	public OrderProduct updateOrderProduct(OrderProduct OrderProduct) {
		return orderProductRepository.save(OrderProduct);
	}
}
