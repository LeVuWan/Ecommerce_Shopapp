package com.project.shopapp.services;

import java.util.List;

import com.project.shopapp.dtos.CategoryDto;
import com.project.shopapp.model.Category;

public interface ICategoryService {
	Category createCategory(CategoryDto category) throws Exception;

	Category getCategoryById(Long id);

	List<Category> getAllCategory();

	Category updateCategory(Long categoryId, CategoryDto category);

	void delete(long id);
}
