package com.cts.transpogov.service;

import java.net.Authenticator.RequestorType;
import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.transpogov.dtos.user.UserCreateRequest;
import com.cts.transpogov.dtos.user.UserResponse;
import com.cts.transpogov.enums.UserRole;
import com.cts.transpogov.models.User;
import com.cts.transpogov.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserLoginServiceImple implements IUserLoginService {

	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private final ModelMapper modelMapper;

	@Override
	public User createUser(UserCreateRequest request) {
		if (request == null) {
			throw new IllegalArgumentException("Request cannot be null");
		}

		if (request.getPhone() != null) {
			throw new IllegalArgumentException("User phone should not be provided while creating a new user");
		}
		User user = modelMapper.map(request, User.class);
		if (user.equals(null)) {
			System.out.println("user is null");
		}

		if (user == null) {
			throw new IllegalStateException("Failed to map UserCreateRequest to User");
		}

		System.out.println(user);

		return userRepository.save(user);

	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public void UpdateUser(User user, Long userId) {

		if (userId == null || userId <= 0) {
			throw new IllegalArgumentException("Invalid user UserId");
		}

		User user2 = userRepository.findById(userId).orElseThrow();
		user2.setCreatedAt(user.getCreatedAt());
		user2.setEmail(user.getEmail());
		user2.setName(user.getName());
		user2.setPassword(user.getPassword());
		user2.setPhone(user.getPhone());
		user2.setRole(user.getRole());
		user2.setStatus(user.getStatus());
		user2.setUpdatedAt(user.getUpdatedAt());
		user2.setUserId(user.getUserId());

		userRepository.save(user2);
		System.out.println("update Information Successfully...!");

	}

	@Override
	public void UpdateUserRoles(UserRole userRole, Long userId) {
		if (userId == null && userRole == null) {
			throw new IllegalArgumentException("Invalid user UserId and UserRole");
		}
		User user2 = userRepository.findById(userId).orElseThrow();
		user2.setRole(userRole);
		userRepository.save(user2);
	}

	@Override
	public User findById(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Invalid user ID");
		}

		return userRepository.findById(id).orElse(null);
	}

}
