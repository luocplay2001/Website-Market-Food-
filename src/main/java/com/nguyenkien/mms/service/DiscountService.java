package com.nguyenkien.mms.service;

import java.util.List;

import com.nguyenkien.mms.model.Discount;

public interface DiscountService {

	Discount updateDiscount(Discount discount);

	void deleteDiscountById(Long id);

	Discount saveDiscount(Discount discount);

	Discount getDiscountById(Long id);

	List<Discount> getAllDiscounts();

}
