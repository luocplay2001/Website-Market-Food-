package com.nguyenkien.mms.service;

import java.util.List;

import com.nguyenkien.mms.model.Restaurant;

public interface RestaurantService {

	List<Restaurant> getAllRestaurants();

	Restaurant getRestaurantById(Long id);

	Restaurant updateRestaurant(Restaurant restaurant);

	void deleteRestaurantById(Long id);

	Restaurant saveRestaurant(Restaurant restaurant);

}
