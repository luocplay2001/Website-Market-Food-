package com.nguyenkien.mms.service;

import java.util.List;

import com.nguyenkien.mms.model.Role;

public interface RoleService {

	Role updateRole(Role role);

	void deleteRoleById(Long id);

	Role saveRole(Role role);

	Role getRoleById(Long id);

	List<Role> getAllRoles();

}
