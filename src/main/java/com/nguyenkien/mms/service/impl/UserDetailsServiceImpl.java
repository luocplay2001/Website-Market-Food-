package com.nguyenkien.mms.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.nguyenkien.mms.model.Shipper;
import com.nguyenkien.mms.model.ShipperRole;
import com.nguyenkien.mms.repository.CustomerRepository;
import com.nguyenkien.mms.repository.CustomerRoleRepository;
import com.nguyenkien.mms.repository.RestaurantRepository;
import com.nguyenkien.mms.repository.RestaurantRoleRepository;
import com.nguyenkien.mms.repository.ShipperRepository;
import com.nguyenkien.mms.repository.ShipperRoleRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CustomerRoleRepository customerRoleRepository;
	
	@Autowired
	private RestaurantRepository restaurantRepository; 
	
	@Autowired
	private RestaurantRoleRepository restaurantRoleRepository;
	
	@Autowired
	private ShipperRepository shipperRepository;
	
	@Autowired
	private ShipperRoleRepository shipperRoleRepository;
	
	
	@Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    	
    	
        Customer customer = this.customerRepository.findByEmail(userName);
        Restaurant restaurant = this.restaurantRepository.findByEmail(userName);
        Shipper shipper = this.shipperRepository.findByEmail(userName);
        UserDetails userDetails;
        if (customer == null && restaurant == null && shipper == null) {
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        } 
        	
        // [ROLE_USER, ROLE_ADMIN,..] 
        List<RestaurantRole> restaurantRoles = this.restaurantRoleRepository.findByRestaurant(restaurant);
        List<CustomerRole> customerRoles = this.customerRoleRepository.findByCustomer(customer);
        List<ShipperRole> shipperRoles = this.shipperRoleRepository.findByShipper(shipper);
        Set<GrantedAuthority> grantList = new HashSet<GrantedAuthority>();
        if (customerRoles != null) {
            for (CustomerRole customerRole : customerRoles) {
                    // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(customerRole.getRole().getRoleName());
                grantList.add(authority);
            }
        }
            
        if (restaurantRoles != null) {
           for (RestaurantRole restaurantRole : restaurantRoles) {
                    // ROLE_USER, ROLE_ADMIN,..
               GrantedAuthority authority = new SimpleGrantedAuthority(restaurantRole.getRole().getRoleName());
               grantList.add(authority);
           }
        }
        
        if (shipperRoles != null) {
            for (ShipperRole shipperRole : shipperRoles) {
                     // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(shipperRole.getRole().getRoleName());
                grantList.add(authority);
            }
         }

        if(shipper != null) {
        	userDetails = (UserDetails) new User(shipper.getEmail(), //
                   shipper.getPassword(), grantList);
        } else if(restaurant != null){
        	userDetails = (UserDetails) new User(restaurant.getEmail(), //
                    restaurant.getPassword(), grantList);
        } else {
        	userDetails = (UserDetails) new User(customer.getEmail(), //
                    customer.getPassword(), grantList);
        }
        

        return userDetails;
	}   
}
