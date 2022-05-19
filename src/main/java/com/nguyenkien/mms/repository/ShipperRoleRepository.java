package com.nguyenkien.mms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nguyenkien.mms.model.Shipper;
import com.nguyenkien.mms.model.ShipperRole;

@Repository
public interface ShipperRoleRepository extends JpaRepository<ShipperRole, Long>{
	List<ShipperRole> findByShipper(Shipper shipper);
}
