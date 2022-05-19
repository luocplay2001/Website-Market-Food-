package com.nguyenkien.mms.service;

import java.util.List;

import com.nguyenkien.mms.model.Order;

public interface OrderService {

	Order updateOrder(Order order);

	void deleteOrderById(Long id);

	Order saveOrder(Order order);

	Order getOrderById(Long id);

	List<Order> getAllOrders();

}
