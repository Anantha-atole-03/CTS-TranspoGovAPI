package com.cts.transpogov.dtos.user;

import com.cts.transpogov.enums.UserRole;
import com.cts.transpogov.enums.UserStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserUpdateRequest {
	@NotBlank
	@NotNull
	private String name;
	@NotEmpty
	private UserRole role;
	@Email
	private String email;
	@NotEmpty
	@NotNull
	@NotBlank
	private String phone;
	@NotBlank
	private UserStatus status;
}
