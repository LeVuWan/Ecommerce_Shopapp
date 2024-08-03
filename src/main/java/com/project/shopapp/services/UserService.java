package com.project.shopapp.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.shopapp.component.JwtTokenUtil;
import com.project.shopapp.dtos.UserDto;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.exceptions.PermissionDenyException;
import com.project.shopapp.model.Role;
import com.project.shopapp.model.User;
import com.project.shopapp.repositories.RoleRepository;
import com.project.shopapp.repositories.UserRepository;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public User createUser(UserDto userDTO) throws Exception {
		// register user
		String phoneNumber = userDTO.getPhoneNumber();
		// Kiểm tra xem số điện thoại đã tồn tại hay chưa
		if (userRepository.existsByPhoneNumber(phoneNumber)) {
			throw new DataIntegrityViolationException("Phone number already exists");
		}
		Role role = roleRepository.findById(userDTO.getRoleId())
				.orElseThrow(() -> new DataNotFoundException("Role not found"));
		if (role.getName().toUpperCase().equals(Role.ADMIN)) {
			throw new PermissionDenyException("You cannot register an admin account");
		}

		User user = new User();

		user.setFullName(userDTO.getFullName());
		user.setPhoneNumber(userDTO.getPhoneNumber());
		user.setPassWord(userDTO.getPassword());
		user.setEmail(userDTO.getEmail());
		user.setAddress(userDTO.getAddress());
		user.setDateOfBirth(userDTO.getDateOfBirth());
		user.setFacebookAccountId(userDTO.getFacebookAccountId());
		user.setGoogleAccountId(userDTO.getGoogleAccountId());

		user.setRole(role);
		// Kiểm tra nếu có accountId, không yêu cầu password
		if (userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) {
			String password = userDTO.getPassword();
			String encodedPassword = passwordEncoder.encode(password);
			user.setPassWord(encodedPassword);
		}
		return userRepository.save(user);
	}

	@Override
	public String login(String phoneNumber, String password) throws Exception {
		Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);

		if (optionalUser.isEmpty()) {
			throw new DataNotFoundException("Invalid phonenumber / password");
		}
		User exitingUser = optionalUser.get();

		if (exitingUser.getFacebookAccountId() == 0 && exitingUser.getGoogleAccountId() == 0) {
			if (!passwordEncoder.matches(password, exitingUser.getPassWord())) {
				throw new BadCredentialsException("Wrong phone number or password");
			}
		}
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(phoneNumber,
				password, exitingUser.getAuthorities());

		authenticationManager.authenticate(authenticationToken);

		return jwtTokenUtil.generateToken(exitingUser);
	}

}
