package com.nguyenkien.mms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nguyenkien.mms.model.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long>{

}
