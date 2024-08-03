package com.project.shopapp.dtos;

import jakarta.validation.constraints.NotEmpty;

public class CategoryDto {
	@NotEmpty(message = "Category name cannot be empty")
	private String name;

	public CategoryDto() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
