package com.nguyenkien.mms.service;

import java.util.List;

import com.nguyenkien.mms.model.ShipperRole;

public interface ShipperRoleService {

	ShipperRole updateShipperRole(ShipperRole shipperRole);

	void deleteShipperRoleById(Long id);

	ShipperRole saveShipperRole(ShipperRole shipperRole);

	ShipperRole getShipperRoleById(Long id);

	List<ShipperRole> getAllShipperRoles();

}
