  package com.cts.transpogov.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.transpogov.dtos.user.UserCreateRequest;
import com.cts.transpogov.enums.UserRole;
import com.cts.transpogov.models.User;
import com.cts.transpogov.repositories.UserRepository;
import com.cts.transpogov.service.UserLoginServiceImple;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
	
	private final UserLoginServiceImple userLoginServiceImple;

	
	@PostMapping("/")
	public ResponseEntity<User> addUserLogin(@RequestBody UserCreateRequest user){
		System.out.println(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(userLoginServiceImple.createUser(user));
	}
	@GetMapping("/")
	public List<User> allUsers(){
		return userLoginServiceImple.getAllUser();
	}
	
	@PutMapping("/{userId}")
	public void updateUser(@RequestBody User user,@PathVariable Long userId) {
		userLoginServiceImple.UpdateUser(user, userId);
		System.out.println("update User successfully");
	}
	@PutMapping("/{userId}/role")
	public void UpdateRole(@RequestBody UserRole userRole,@PathVariable Long userId) {
		userLoginServiceImple.UpdateUserRoles(userRole, userId);
		
	}
	@GetMapping("/{id}")
	public User getuser(@PathVariable Long id) {
	return userLoginServiceImple.findById(id);
	}
	

}
