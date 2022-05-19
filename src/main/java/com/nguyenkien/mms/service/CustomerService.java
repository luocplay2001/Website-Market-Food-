package com.nguyenkien.mms.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nguyenkien.mms.model.Customer;

public interface CustomerService {

	Customer updateCustomer(Customer customer);

	void deleteCustomerById(Long id);

	Customer saveCustomer(Customer customer);

	Customer getCustomerById(Long id);

	List<Customer> getAllCustomers();

}
