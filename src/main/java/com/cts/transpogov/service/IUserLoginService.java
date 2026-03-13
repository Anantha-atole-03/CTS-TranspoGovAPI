package com.cts.transpogov.service;

import java.util.List;

import com.cts.transpogov.dtos.user.UserCreateRequest;
import com.cts.transpogov.dtos.user.UserResponse;
import com.cts.transpogov.enums.UserRole;
import com.cts.transpogov.models.User;

public interface IUserLoginService {
	
	UserResponse addUser(UserCreateRequest user);
	List<User>getAllUser();
	void UpdateUser(User user,Long userId);
	void UpdateUserRoles(UserRole userRole, Long userId);

}
