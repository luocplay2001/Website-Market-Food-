package com.nguyenkien.mms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nguyenkien.mms.model.Category;
import com.nguyenkien.mms.repository.CategoryRepository;
import com.nguyenkien.mms.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository; 
	
	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}
	
	@Override
	public Category getCategoryById(Long id) {
		return categoryRepository.findById(id).get();
	}
	
	@Override
	public Category saveCategory(Category category) {
		return categoryRepository.save(category);
	}
	
	@Override
	public void deleteCategoryById(Long id) {
		categoryRepository.deleteById(id);	
	}
	
	@Override
	public Category updateCategory(Category category) {
		return categoryRepository.save(category);
	}
}
