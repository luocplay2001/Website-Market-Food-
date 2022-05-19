package com.nguyenkien.mms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nguyenkien.mms.model.CustomerRole;
import com.nguyenkien.mms.repository.CustomerRoleRepository;
import com.nguyenkien.mms.service.CustomerRoleService;

@Service
public class CustomerRoleServiceImpl implements CustomerRoleService{
	@Autowired
	private CustomerRoleRepository customerRoleRepository; 
	
	@Override
	public List<CustomerRole> getAllCustomerRoles() {
		return customerRoleRepository.findAll();
	}
	
	@Override
	public CustomerRole getCustomerRoleById(Long id) {
		return customerRoleRepository.findById(id).get();
	}
	
	@Override
	public CustomerRole saveCustomerRole(CustomerRole customerRole) {
		return customerRoleRepository.save(customerRole);
	}
	
	@Override
	public void deleteCustomerRoleById(Long id) {
		customerRoleRepository.deleteById(id);	
	}
	
	@Override
	public CustomerRole updateCustomerRole(CustomerRole customerRole) {
		return customerRoleRepository.save(customerRole);
	}
}
