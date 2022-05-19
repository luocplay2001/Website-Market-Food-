package com.nguyenkien.mms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nguyenkien.mms.model.Restaurant;
import com.nguyenkien.mms.model.RestaurantRole;

@Repository
public interface RestaurantRoleRepository extends JpaRepository<RestaurantRole, Long>{
	List<RestaurantRole> findByRestaurant(Restaurant restaurant);
}
