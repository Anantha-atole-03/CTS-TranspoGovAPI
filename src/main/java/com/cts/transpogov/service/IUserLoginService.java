package com.cts.transpogov.service;

import java.util.List;

import com.cts.transpogov.dtos.user.UserCreateRequest;
import com.cts.transpogov.enums.UserRole;
import com.cts.transpogov.models.User;

public interface IUserLoginService {

	User createUser(UserCreateRequest user);

	List<User> getAllUser();

	void updateUser(User user, Long userId);

	void updateUserRoles(UserRole userRole, Long userId);

	User findById(Long id);

}
