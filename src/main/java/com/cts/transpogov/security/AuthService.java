package com.cts.transpogov.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.cts.transpogov.dtos.LoginRequestDto;
import com.cts.transpogov.dtos.LoginResponseDto;
import com.cts.transpogov.models.Citizen;
import com.cts.transpogov.models.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final AuthenticationManager authenticationManager;
	private final AuthUtils authUtils;

	public LoginResponseDto login(LoginRequestDto loginRequestDto) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequestDto.getPhone(), loginRequestDto.getPassword()));

		LoginResponseDto loginResponseDto = new LoginResponseDto();
		if (authentication.getPrincipal() instanceof User user) {
//			User user = (User) authentication.getPrincipal();
			loginResponseDto.setPhone(user.getPhone());
			loginResponseDto.setRole(user.getRole());
		} else {
			Citizen citizen = (Citizen) authentication.getPrincipal();
			loginResponseDto.setPhone(citizen.getPhone());
			loginResponseDto.setRole(citizen.getRole());
		}
		String token = authUtils.generateAccessToken((UserDetails) authentication.getPrincipal());
		loginResponseDto.setToken(token);
		return loginResponseDto;
	}
}
