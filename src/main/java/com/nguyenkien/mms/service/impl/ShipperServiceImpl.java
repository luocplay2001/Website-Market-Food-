package com.nguyenkien.mms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nguyenkien.mms.model.Shipper;
import com.nguyenkien.mms.repository.ShipperRepository;
import com.nguyenkien.mms.service.ShipperService;

@Service
public class ShipperServiceImpl implements ShipperService{
	
	@Autowired
	private ShipperRepository shipperRepository; 
	
	
	@Override
	public List<Shipper> getAllShippers() {
		return shipperRepository.findAll();
	}
	
	@Override
	public Shipper getShipperById(Long id) {
		return shipperRepository.findById(id).get();
	}
	
	@Override
	public Shipper saveShipper(Shipper shipper) {
		return shipperRepository.save(shipper);
	}
	
	@Override
	public void deleteShipperById(Long id) {
		shipperRepository.deleteById(id);	
	}
	
	@Override
	public Shipper updateShipper(Shipper shipper) {
		return shipperRepository.save(shipper);
	}
}
