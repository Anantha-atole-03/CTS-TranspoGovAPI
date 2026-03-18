package com.cts.transpogov.dtos.auth;

import com.cts.transpogov.enums.UserRole;

import lombok.Data;
@Data
public class LoginRequestDto {
	private String phone;
	private String password;
	private UserRole role;
}
