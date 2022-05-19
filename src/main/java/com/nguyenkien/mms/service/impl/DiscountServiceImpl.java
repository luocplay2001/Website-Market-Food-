package com.nguyenkien.mms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nguyenkien.mms.model.Discount;
import com.nguyenkien.mms.repository.DiscountRepository;
import com.nguyenkien.mms.service.DiscountService;

@Service
public class DiscountServiceImpl implements DiscountService{
	
	@Autowired
	private DiscountRepository discountRepository; 
	
	@Override
	public List<Discount> getAllDiscounts() {
		return discountRepository.findAll();
	}
	
	@Override
	public Discount getDiscountById(Long id) {
		return discountRepository.findById(id).get();
	}
	
	@Override
	public Discount saveDiscount(Discount discount) {
		return discountRepository.save(discount);
	}
	
	@Override
	public void deleteDiscountById(Long id) {
		discountRepository.deleteById(id);	
	}
	
	@Override
	public Discount updateDiscount(Discount discount) {
		return discountRepository.save(discount);
	}
}
