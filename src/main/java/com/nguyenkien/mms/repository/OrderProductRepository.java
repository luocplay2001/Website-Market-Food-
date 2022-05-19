package com.nguyenkien.mms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nguyenkien.mms.model.Customer;
import com.nguyenkien.mms.model.CustomerRole;
import com.nguyenkien.mms.model.Order;
import com.nguyenkien.mms.model.OrderProduct;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>{
	List<OrderProduct> findByOrder(Order order);
}
