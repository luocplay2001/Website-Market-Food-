package com.nguyenkien.mms.service;

import java.util.List;

import com.nguyenkien.mms.model.CustomerRole;

public interface CustomerRoleService {

	CustomerRole updateCustomerRole(CustomerRole customerRole);

	void deleteCustomerRoleById(Long id);

	CustomerRole saveCustomerRole(CustomerRole customerRole);

	CustomerRole getCustomerRoleById(Long id);

	List<CustomerRole> getAllCustomerRoles();

}
