package com.nguyenkien.mms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nguyenkien.mms.model.RestaurantRole;
import com.nguyenkien.mms.repository.RestaurantRoleRepository;
import com.nguyenkien.mms.service.RestaurantRoleService;

@Service
public class RestaurantRoleServiceImpl implements RestaurantRoleService{
	@Autowired
	private RestaurantRoleRepository restaurantRoleRepository; 
	
	@Override
	public List<RestaurantRole> getAllRestaurantRoles() {
		return restaurantRoleRepository.findAll();
	}
	
	@Override
	public RestaurantRole getRestaurantRoleById(Long id) {
		return restaurantRoleRepository.findById(id).get();
	}
	
	@Override
	public RestaurantRole saveRestaurantRole(RestaurantRole restaurantRole) {
		return restaurantRoleRepository.save(restaurantRole);
	}
	
	@Override
	public void deleteRestaurantRoleById(Long id) {
		restaurantRoleRepository.deleteById(id);	
	}
	
	@Override
	public RestaurantRole updateRestaurantRole(RestaurantRole restaurantRole) {
		return restaurantRoleRepository.save(restaurantRole);
	}
}
