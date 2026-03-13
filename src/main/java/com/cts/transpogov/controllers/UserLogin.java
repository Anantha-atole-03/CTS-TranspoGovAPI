package com.cts.transpogov.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.transpogov.dtos.user.UserCreateRequest;
import com.cts.transpogov.dtos.user.UserResponse;
import com.cts.transpogov.service.UserLoginServiceImple;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/login/users")
@RestController
@RequiredArgsConstructor
public class UserLogin {
	
	UserLoginServiceImple userLoginServiceImple;
	
	@GetMapping
	public ResponseEntity<UserResponse> addUser(@RequestBody UserCreateRequest user){
		return new ResponseEntity<UserResponse>(HttpStatus.CREATED);
		
	}
	
	

}
