package com.cts.transpogov.dtos.auth;

import com.cts.transpogov.enums.UserRole;

import lombok.Data;

@Data
public class LoginResponseDto {
	private String phone;
	private UserRole role;
	private String token;
}
