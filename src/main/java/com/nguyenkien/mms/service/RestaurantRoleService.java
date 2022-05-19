package com.nguyenkien.mms.service;

import java.util.List;

import com.nguyenkien.mms.model.RestaurantRole;

public interface RestaurantRoleService {

	RestaurantRole updateRestaurantRole(RestaurantRole restaurantRole);

	void deleteRestaurantRoleById(Long id);

	RestaurantRole saveRestaurantRole(RestaurantRole restaurantRole);

	RestaurantRole getRestaurantRoleById(Long id);

	List<RestaurantRole> getAllRestaurantRoles();

}
