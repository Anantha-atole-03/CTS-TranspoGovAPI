package com.cts.transpogov.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.transpogov.dtos.auth.LoginRequestDto;
import com.cts.transpogov.dtos.citizen.CitizenCreateRequest;
import com.cts.transpogov.service.IAuthService;
import com.cts.transpogov.service.ICitizenService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final IAuthService authService;
	private final ICitizenService citizenService;
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDto request){
		
		
		return ResponseEntity.ok(authService.login(request));
	}
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody CitizenCreateRequest createRequest){
		return ResponseEntity.ok(citizenService.addCitizen(createRequest));
	}
}