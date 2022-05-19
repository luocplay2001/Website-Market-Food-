package com.nguyenkien.mms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nguyenkien.mms.model.ShipperRole;
import com.nguyenkien.mms.repository.ShipperRoleRepository;
import com.nguyenkien.mms.service.ShipperRoleService;

@Service
public class ShipperRoleServiceImpl implements ShipperRoleService{
	@Autowired
	private ShipperRoleRepository shipperRoleRepository; 
	
	@Override
	public List<ShipperRole> getAllShipperRoles() {
		return shipperRoleRepository.findAll();
	}
	
	@Override
	public ShipperRole getShipperRoleById(Long id) {
		return shipperRoleRepository.findById(id).get();
	}
	
	@Override
	public ShipperRole saveShipperRole(ShipperRole shipperRole) {
		return shipperRoleRepository.save(shipperRole);
	}
	
	@Override
	public void deleteShipperRoleById(Long id) {
		shipperRoleRepository.deleteById(id);	
	}
	
	@Override
	public ShipperRole updateShipperRole(ShipperRole shipperRole) {
		return shipperRoleRepository.save(shipperRole);
	}
}
