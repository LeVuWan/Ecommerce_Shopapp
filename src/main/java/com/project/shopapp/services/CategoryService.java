package com.project.shopapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.shopapp.dtos.CategoryDto;
import com.project.shopapp.model.Category;
import com.project.shopapp.repositories.CategoryRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoryService implements ICategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Category createCategory(CategoryDto categoryDto) {
		Category newCategory = new Category(categoryDto.getName());
		return categoryRepository.save(newCategory);
	}

	@Override
	public Category getCategoryById(Long id) {
		return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
	}

	@Override
	public List<Category> getAllCategory() {
		return categoryRepository.findAll();
	}

	@Override
	public void delete(long id) {
		categoryRepository.deleteById(id);
	}

	@Override
	@Transactional
	public Category updateCategory(Long categoryId, CategoryDto category) {
		Category existingCategory = getCategoryById(categoryId);
		existingCategory.setName(category.getName());
		categoryRepository.save(existingCategory);
		return existingCategory;
	}

}
