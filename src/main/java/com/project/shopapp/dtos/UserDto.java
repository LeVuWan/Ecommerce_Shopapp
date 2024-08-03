package com.project.shopapp.dtos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDto {
	@JsonProperty("fullname")
	private String fullName;

	@JsonProperty("phone_number")
	@NotBlank(message = "Phone number is required")
	private String phoneNumber;

	private String address;

	@NotBlank(message = "Password cannot be blank")
	private String password;

	@NotNull(message = "Email cannot be null")
	@Email(message = "Email should be valid")
	@JsonProperty("email")
	private String email;

	@JsonProperty("retype_password")
	private String retypePassword;

	@JsonProperty("date_of_birth")
	private Date dateOfBirth;

	@JsonProperty("facebook_account_id")
	private int facebookAccountId;

	@JsonProperty("google_account_id")
	private int googleAccountId;

	@NotNull(message = "Role ID is required")
	@JsonProperty("role_id")
	private Long roleId;

	public UserDto() {
		// TODO Auto-generated constructor stub
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRetypePassword() {
		return retypePassword;
	}

	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getFacebookAccountId() {
		return facebookAccountId;
	}

	public void setFacebookAccountId(int facebookAccountId) {
		this.facebookAccountId = facebookAccountId;
	}

	public int getGoogleAccountId() {
		return googleAccountId;
	}

	public void setGoogleAccountId(int googleAccountId) {
		this.googleAccountId = googleAccountId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
