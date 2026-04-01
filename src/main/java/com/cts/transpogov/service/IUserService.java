package com.cts.transpogov.service;

import java.util.List;

import com.cts.transpogov.dtos.user.ForgotPasswordRequest;
import com.cts.transpogov.dtos.user.UserCreateRequest;
import com.cts.transpogov.dtos.user.UserResponse;
import com.cts.transpogov.models.User;

public interface IUserService {
	UserResponse save(UserCreateRequest requestDto);

	List<User> getAllUsers();

	void forgotPassword(ForgotPasswordRequest request);

	

}
