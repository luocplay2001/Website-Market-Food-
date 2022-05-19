package com.nguyenkien.mms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nguyenkien.mms.model.Role;
import com.nguyenkien.mms.repository.RoleRepository;
import com.nguyenkien.mms.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	private RoleRepository roleRepository;
		
	@Override
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}
	
	@Override
	public Role getRoleById(Long id) {
		return roleRepository.findById(id).get();
	}
	
	@Override
	public Role saveRole(Role role) {
		return roleRepository.save(role);
	}
	
	@Override
	public void deleteRoleById(Long id) {
		roleRepository.deleteById(id);	
	}
	
	@Override
	public Role updateRole(Role role) {
		return roleRepository.save(role);
	}
}
