package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class ProductImageDto {

	@JsonProperty("product_id")
	@Min(value = 1, message = "Product's ID must be >0")
	private Long productId;

	@Size(min = 5, max = 200, message = "Image's name")
	@JsonProperty("image_url")
	private String imageUrl;

	public ProductImageDto() {
		// TODO Auto-generated constructor stub
	}

	public ProductImageDto(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
