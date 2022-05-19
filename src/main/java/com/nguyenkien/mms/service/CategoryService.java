package com.nguyenkien.mms.service;

import java.util.List;

import com.nguyenkien.mms.model.Category;

public interface CategoryService {

	Category updateCategory(Category category);

	void deleteCategoryById(Long id);

	Category saveCategory(Category category);

	Category getCategoryById(Long id);

	List<Category> getAllCategories();

}
