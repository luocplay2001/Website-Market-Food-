package com.nguyenkien.mms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nguyenkien.mms.model.Customer;
import com.nguyenkien.mms.model.CustomerRole;

@Repository
public interface CustomerRoleRepository extends JpaRepository<CustomerRole, Long>{
	List<CustomerRole> findByCustomer(Customer customer);  
}
