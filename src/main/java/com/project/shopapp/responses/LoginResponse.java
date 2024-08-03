package com.project.shopapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public class LoginResponse {
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("token")
	private String token;
	
	public LoginResponse() {
		// TODO Auto-generated constructor stub
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
