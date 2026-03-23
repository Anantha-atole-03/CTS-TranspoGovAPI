package com.cts.transpogov.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.transpogov.dtos.LoginRequestDto;
import com.cts.transpogov.dtos.LoginResponseDto;
import com.cts.transpogov.dtos.citizen.CitizenCreateRequest;
import com.cts.transpogov.dtos.citizen.CitizenResponse;
import com.cts.transpogov.dtos.user.UserCreateRequest;
import com.cts.transpogov.dtos.user.UserResponse;
import com.cts.transpogov.security.AuthService;
import com.cts.transpogov.service.ICitizenService;
import com.cts.transpogov.service.IUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
	private final IUserService userService;
	private final ICitizenService citizenService;

	@PostMapping("/user/signup")
	public ResponseEntity<UserResponse> userSignup(@RequestBody UserCreateRequest requestDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(requestDto));
	}

	@PostMapping("/citizen/signup")
	public ResponseEntity<CitizenResponse> citizenSignup(@RequestBody CitizenCreateRequest dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(citizenService.save(dto));
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
		return ResponseEntity.ok(authService.login(loginRequestDto));
	}
}
