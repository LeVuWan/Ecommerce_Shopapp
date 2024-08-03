package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class OrderDetailDto {
	@JsonProperty("order_id")
	@NotNull(message = "Order ID cannot be null")
	private Long orderId;

	@JsonProperty("product_id")
	@NotNull(message = "Product ID cannot be null")
	private Long productId;

	@JsonProperty("number_of_product")
	@Min(value = 1, message = "Number of products must be at least 1")
	private int numberOfProduct;

	@JsonProperty("total_money")
	@Positive(message = "Total money must be positive")
	private Float totalMoney;

	@JsonProperty("color")
	@NotBlank(message = "Color cannot be blank")
	private String color;
	
	@Min(value=0, message = "Product's ID must be >= 0")
    private Float price;
	
	public OrderDetailDto() {
		// TODO Auto-generated constructor stub
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public int getNumberOfProduct() {
		return numberOfProduct;
	}

	public void setNumberOfProduct(int numberOfProduct) {
		this.numberOfProduct = numberOfProduct;
	}

	public Float getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Float totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "OrderDetailDtos [orderId=" + orderId + ", productId=" + productId + ", numberOfProduct="
				+ numberOfProduct + ", totalMoney=" + totalMoney + ", color=" + color + "]";
	}
	
	
	
}
