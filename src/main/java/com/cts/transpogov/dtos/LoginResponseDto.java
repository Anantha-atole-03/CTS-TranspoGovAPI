package com.cts.transpogov.dtos;

import com.cts.transpogov.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
	private String phone;
	private UserRole role;
	private String token;
}
