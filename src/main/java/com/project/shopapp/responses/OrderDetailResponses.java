package com.project.shopapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.model.OrderDetail;

public class OrderDetailResponses {
	private Long id;

	@JsonProperty("order_id")
	private Long orderId;

	@JsonProperty("product_id")
	private Long productId;

	@JsonProperty("price")
	private Float price;

	@JsonProperty("number_of_products")
	private int numberOfProducts;

	@JsonProperty("total_money")
	private Float totalMoney;

	@JsonProperty("color")
	private String color;

	public OrderDetailResponses() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public int getNumberOfProducts() {
		return numberOfProducts;
	}

	public void setNumberOfProducts(int numberOfProducts) {
		this.numberOfProducts = numberOfProducts;
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

	public static OrderDetailResponses fromOrderDetail(OrderDetail orderDetail) {
		OrderDetailResponses orderDetailResponses = new OrderDetailResponses();
		orderDetailResponses.setId(orderDetail.getId());
		orderDetailResponses.setProductId(orderDetail.getProduct().getId());
		orderDetailResponses.setOrderId(orderDetail.getOrder().getId());
		orderDetailResponses.setPrice(orderDetail.getPrice());
		orderDetailResponses.setNumberOfProducts(orderDetail.getNumberOfProducts());
		orderDetailResponses.setColor(orderDetail.getColor());
		orderDetailResponses.setTotalMoney(orderDetail.getTotalMoney());
		
		return orderDetailResponses;
	}
}
