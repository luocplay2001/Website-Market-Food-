package com.nguyenkien.mms.service;

import java.util.List;

import com.nguyenkien.mms.model.OrderProduct;

public interface OrderProductService {

	OrderProduct updateOrderProduct(OrderProduct OrderProduct);

	void deleteOrderProductById(Long id);

	OrderProduct saveOrderProduct(OrderProduct orderProduct);

	OrderProduct getOrderProductById(Long id);

	List<OrderProduct> getAllOrderProducts();

}
