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
		User user=modelMapper.map(request, User.class);
		System.out.println(user);
//		User user = User.builder().name(request.getName())
//				.role(request.getRole())
//				.email(request.getEmail())
//				.phone(request.getPhone())
//				.password(request.getPassword())
//				.status(request.getStatus())
//				.createdAt(LocalDateTime.now())
//				.updatedAt(LocalDateTime.now())
//				.build();
		
		return userRepository.save(user);
                

      
		 
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public void UpdateUser(User user, Long userId) {

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
		User user2 = userRepository.findById(userId).orElseThrow();
		user2.setRole(userRole);
		userRepository.save(user2);
	}

	@Override
	public User findById(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id).orElse(null);
	}

}
