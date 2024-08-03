package com.project.shopapp.services;

import com.project.shopapp.dtos.UserDto;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.model.User;

public interface IUserService {
	User createUser(UserDto userDtos) throws Exception;

	String login(String phoneNumber, String password) throws Exception;
}
