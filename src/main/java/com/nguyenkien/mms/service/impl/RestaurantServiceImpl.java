package com.nguyenkien.mms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nguyenkien.mms.model.Customer;
import com.nguyenkien.mms.model.CustomerRole;
import com.nguyenkien.mms.model.Restaurant;
import com.nguyenkien.mms.model.RestaurantRole;
import com.nguyenkien.mms.repository.RestaurantRepository;
import com.nguyenkien.mms.repository.RestaurantRoleRepository;
import com.nguyenkien.mms.service.RestaurantService;


@Service
public class RestaurantServiceImpl implements RestaurantService{
	
	@Autowired
	private RestaurantRepository restaurantRepository; 
	
	@Override
	public List<Restaurant> getAllRestaurants() {
		return restaurantRepository.findAll();
	}
	
	@Override
	public Restaurant getRestaurantById(Long id) {
		return restaurantRepository.findById(id).get();
	}
	
	@Override
	public Restaurant saveRestaurant(Restaurant restaurant) {
		return restaurantRepository.save(restaurant);
	}
	
	@Override
	public void deleteRestaurantById(Long id) {
		restaurantRepository.deleteById(id);	
	}
	
	@Override
	public Restaurant updateRestaurant(Restaurant restaurant) {
		return restaurantRepository.save(restaurant);
	}
}
