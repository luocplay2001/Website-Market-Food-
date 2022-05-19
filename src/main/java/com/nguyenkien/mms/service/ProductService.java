package com.nguyenkien.mms.service;

import java.util.List;

import com.nguyenkien.mms.model.Product;

public interface ProductService {

	Product updateProduct(Product product);

	void deleteProductById(Long id);

	Product saveProduct(Product product);

	Product getProductById(Long id);

	List<Product> getAllProducts();

}
