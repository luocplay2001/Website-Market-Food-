package com.nguyenkien.mms.service;

import java.util.List;

import com.nguyenkien.mms.model.Shipper;

public interface ShipperService {

	Shipper updateShipper(Shipper shipper);

	void deleteShipperById(Long id);

	Shipper saveShipper(Shipper shipper);

	Shipper getShipperById(Long id);

	List<Shipper> getAllShippers();

}
