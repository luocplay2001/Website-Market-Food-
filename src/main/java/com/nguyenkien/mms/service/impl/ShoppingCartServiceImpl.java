package com.nguyenkien.mms.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.nguyenkien.mms.model.CartItem;
import com.nguyenkien.mms.service.ShoppingCartService;

@Service
@SessionScope
public class ShoppingCartServiceImpl implements ShoppingCartService{
	private Map<Long, CartItem> map = new HashMap<>();
	
	@Override
	public void add(CartItem item) {
		CartItem existedItem = map.get(item.getProductId());
		
		if(existedItem != null) {
			existedItem.setQuantity(item.getQuantity() + existedItem.getQuantity());
		} else {
			map.put(item.getProductId(), item);
		}
	}
	
	@Override
	public Collection<CartItem> getCartItem() {
		return map.values();
	}
	
	@Override
	public double getAmount() {
		return map.values().stream().mapToDouble(item->item.getQuantity() * item.getPrice()).sum();
	}
	
	@Override
	public int getCount() {
		if(map.isEmpty())
			return 0;
		Collection<CartItem> cartItems = getCartItem();
		int count = 0;
		for(CartItem cartItem : cartItems) {
			count += cartItem.getQuantity();
		}
		return count;
	}
	
	@Override
	public void update(Long productId, int quantity) {
		CartItem item = map.get(productId);
		item.setQuantity(quantity);
		// 
		if(item.getQuantity() == 0) 
			map.remove(productId);
	}
	
	@Override
	public void remove(Long productId) {
		CartItem item = map.get(productId);
		if(item.getQuantity() >= 2) {
			int temp = item.getQuantity();
			item.setQuantity(temp - 1);
			map.put(productId, item);
		} else {
			map.remove(productId);
		}
	}
	
	@Override
	public void clear() {
		map.clear();
	}

}
