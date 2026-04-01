package com.cts.transpogov.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cts.transpogov.dtos.user.UserCreateRequest;
import com.cts.transpogov.enums.UserRole;
import com.cts.transpogov.models.User;
import com.cts.transpogov.service.UserLoginServiceImple;

import lombok.RequiredArgsConstructor;

/**
 * REST Controller for managing User accounts and authentication details.
 * Handles user registration, profile updates, and role management.
 */
@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserLoginServiceImple userLoginServiceImple;

	/**
	 * Registers a new user in the system. * @param user DTO containing the initial
	 * user details (username, password, etc.).
	 * 
	 * @return ResponseEntity containing the created User object and HTTP 201
	 *         Created status.
	 */
	@PostMapping("/")
	public ResponseEntity<User> addUser(@RequestBody UserCreateRequest user) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userLoginServiceImple.createUser(user));
	}

	/**
	 * Retrieves a list of all users registered in the system. * @return List of all
	 * User entities.
	 */
	@GetMapping("/")
	public List<User> allUsers() {
		return userLoginServiceImple.getAllUser();
	}

	/**
	 * Updates an existing user's profile information. * @param user The user object
	 * containing updated fields.
	 * 
	 * @param userId The unique ID of the user to be updated.
	 */
	@PutMapping("/{userId}")
	public void updateUser(@RequestBody User user, @PathVariable Long userId) {
		userLoginServiceImple.updateUser(user, userId);
		System.out.println("User updated successfully");
	}

	/**
	 * Updates the specific role/authority of a user. * @param userRole The new role
	 * to assign (e.g., ADMIN, CITIZEN).
	 * 
	 * @param userId The unique ID of the user.
	 */
	@PutMapping("/{userId}/role")
	public void updateRole(@RequestBody UserRole userRole, @PathVariable Long userId) {
		userLoginServiceImple.updateUserRoles(userRole, userId);
	}

	/**
	 * Fetches the details of a specific user by their unique identifier. * @param
	 * id The unique ID of the user.
	 * 
	 * @return The User entity matching the provided ID.
	 */
	@GetMapping("/{id}")
	public User getuser(@PathVariable Long id) {
		return userLoginServiceImple.findById(id);
	}
}