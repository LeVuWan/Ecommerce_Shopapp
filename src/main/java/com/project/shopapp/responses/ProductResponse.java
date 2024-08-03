package com.project.shopapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.model.Product;

public class ProductResponse extends BaseResponse {
	private String name;
	private Float price;
	private String thumbnail;
	private String description;
	@JsonProperty("category_id")
	private Long categoryId;

	public ProductResponse() {
		// TODO Auto-generated constructor stub
	}

	public static ProductResponse fromProduct(Product product) {
		ProductResponse productResponse = new ProductResponse();
		productResponse.setName(product.getName());
		productResponse.setPrice(product.getPrice());
		productResponse.setThumbnail(product.getThumbnail());
		productResponse.setDescription(product.getDescription());
		productResponse.setCategoryId(product.getCategory().getId());
		productResponse.setCreatedAt(product.getCreateAt());
		productResponse.setUpdatedAt(product.getUpdateDate());
		return productResponse;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
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

}
