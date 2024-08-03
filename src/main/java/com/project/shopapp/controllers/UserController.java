package com.project.shopapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.shopapp.components.LocalizationUtils;
import com.project.shopapp.dtos.UserDto;
import com.project.shopapp.dtos.UserLoginDto;
import com.project.shopapp.model.User;
import com.project.shopapp.responses.LoginResponse;
import com.project.shopapp.services.IUserService;
import com.project.shopapp.utils.MessageKeys;

import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IUserService userService;

	@PostMapping("/register")
	// can we register an "admin" user ?
	public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDTO, BindingResult result) {
		try {
			if (result.hasErrors()) {
				List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage)
						.toList();
				return ResponseEntity.badRequest().body(errorMessages);
			}
			if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
				return ResponseEntity.badRequest().body("Password does not match");
			}
			User user = userService.createUser(userDTO);
			// return ResponseEntity.ok("Register successfully");
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody UserLoginDto userLoginDTO) {
		// Kiểm tra thông tin đăng nhập và sinh token
		try {
			String token = userService.login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword(),
					userLoginDTO.getRoleId());
			// Trả về token trong response
			return ResponseEntity.ok(LoginResponse.builder()
					.message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_SUCCESSFULLY)).token(token)
					.build());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(LoginResponse.builder()
					.message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_FAILED, e.getMessage())).build());
		}
	}

}
