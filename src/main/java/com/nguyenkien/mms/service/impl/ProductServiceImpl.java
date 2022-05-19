package com.nguyenkien.mms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nguyenkien.mms.model.Product;
import com.nguyenkien.mms.repository.ProductRepository;
import com.nguyenkien.mms.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository productRepository; 
	
	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	@Override
	public Product getProductById(Long id) {
		return productRepository.findById(id).get();
	}
	
	@Override
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}
	
	@Override
	public void deleteProductById(Long id) {
		productRepository.deleteById(id);	
	}
	
	@Override
	public Product updateProduct(Product product) {
		return productRepository.save(product);
	}
}
