package com.nguyenkien.mms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nguyenkien.mms.model.Shipper;

@Repository
public interface ShipperRepository extends JpaRepository<Shipper, Long>{
	Shipper findByEmail(String userName);
}
