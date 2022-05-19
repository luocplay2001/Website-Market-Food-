package com.nguyenkien.mms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nguyenkien.mms.model.Customer;
import com.nguyenkien.mms.repository.CustomerRepository;
import com.nguyenkien.mms.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerRepository customerRepository;
		
	@Override
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}
	
	@Override
	public Customer getCustomerById(Long id) {
		return customerRepository.findById(id).get();
	}
	
	@Override
	public Customer saveCustomer(Customer customer) {
		return customerRepository.save(customer);
	}
	
	@Override
	public void deleteCustomerById(Long id) {
		customerRepository.deleteById(id);	
	}
	
	@Override
	public Customer updateCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

 	

}
