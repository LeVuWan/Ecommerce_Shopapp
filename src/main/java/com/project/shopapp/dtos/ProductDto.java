package com.project.shopapp.dtos;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.AssertFalse.List;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProductDto {
	@NotBlank(message = "Title is required")
	@Size(min = 3, max = 200, message = "Title must be between 3 and 200 charaters")
	private String name;
	
	@Min(value = 0, message = "Price must be greater than or equal to 0")
	@Max(value = 10000000, message = "Price must be less than or equal to 10.000.000")
	private float price;
	
	
	private String thumbnail;
	private String description;

	@JsonProperty("category_id")
	private Long categoryId;
	private java.util.List<MultipartFile> file;

	public ProductDto() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public java.util.List<MultipartFile> getFile() {
		return file;
	}

	public void setFile(java.util.List<MultipartFile> file) {
		this.file = file;
	}


	
	

}
