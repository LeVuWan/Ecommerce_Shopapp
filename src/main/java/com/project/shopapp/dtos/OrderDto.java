package com.project.shopapp.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class OrderDto {
	@JsonProperty("user_id")
	@NotNull(message = "User ID cannot be null")
	@Min(value = 1, message = "User ID must be a positive number")
	private Long userId;

	@JsonProperty("full_name")
	@NotNull(message = "Full name cannot be null")
	@Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
	private String fullName;

	@JsonProperty("email")
	@NotNull(message = "Email cannot be null")
	@Email(message = "Email should be valid")
	private String email;

	@JsonProperty("phone_number")
	@NotNull(message = "Phone number cannot be null")
	@Pattern(regexp = "\\d{10,15}", message = "Phone number must be between 10 and 15 digits")
	private String phoneNumber;

	@JsonProperty("address")
	@NotNull(message = "Address cannot be null")
	@Size(min = 5, max = 200, message = "Address must be between 5 and 200 characters")
	private String address;

	@JsonProperty("note")
	@Size(max = 500, message = "Note must be less than 500 characters")
	private String note;

	@JsonProperty("total_money")
	@NotNull(message = "Total money cannot be null")
	@DecimalMin(value = "0.0", inclusive = false, message = "Total money must be greater than zero")
	private Float totalMoney;

	@JsonProperty("shipping_method")
	@NotNull(message = "Shipping method cannot be null")
	@Size(min = 1, max = 100, message = "Shipping method must be between 1 and 100 characters")
	private String shippingMethod;

	@JsonProperty("payment_method")
	@NotNull(message = "Payment method cannot be null")
	@Size(min = 1, max = 100, message = "Payment method must be between 1 and 100 characters")
	private String paymentMethod;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate shippingDate;

	public OrderDto() {
		// TODO Auto-generated constructor stub
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Float getTotalMoney() {
		return totalMoney;
	}

	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public void setTotalMoney(Float totalMoney) {
		this.totalMoney = totalMoney;
	}

	public LocalDate getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(LocalDate shippingDate) {
		this.shippingDate = shippingDate;
	}

}
