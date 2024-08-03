package com.project.shopapp.controllers;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.shopapp.dtos.CategoryDto;
import com.project.shopapp.model.Category;
import com.project.shopapp.services.CategoryService;

import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}/categories")
//@Validated
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("")
	public ResponseEntity<?> careateCategory(@Valid @RequestBody CategoryDto categoryDtos, BindingResult result) {
		if(result.hasErrors()) {
	        List<String> errorMessages = result.getFieldErrors()
	            .stream()
	            .map(FieldError::getDefaultMessage)
	            .toList();
	        return ResponseEntity.badRequest().body(errorMessages);
	    }
		categoryService.createCategory(categoryDtos);
		return ResponseEntity.ok("Insert category successfully");
	}
	
	@GetMapping("")
	public ResponseEntity<List<Category>> getAllCategory(@RequestParam("page") int page, @RequestParam("limit") int limit) {
		List<Category> categories = categoryService.getAllCategory();
		return ResponseEntity.ok(categories);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateCategory(@PathVariable long id,@RequestBody CategoryDto categoryDtos) {
		categoryService.updateCategory(id, categoryDtos);
		return ResponseEntity.ok("Update category successfully");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable long id) {
		categoryService.delete(id);
		return ResponseEntity.ok("Insert category successfully");
	}
}
