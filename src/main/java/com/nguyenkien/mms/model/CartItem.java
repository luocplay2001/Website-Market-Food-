package com.nguyenkien.mms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
	private Long productId;
	private String name;
	private String image;
	private String description;
	private int quantity;
	private double price;
}
