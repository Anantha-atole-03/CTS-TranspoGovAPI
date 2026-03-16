package com.cts.transpogov.dtos.user;

import com.cts.transpogov.enums.UserRole;
import com.cts.transpogov.enums.UserStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {
	@NotBlank
	@NotNull
	@NotEmpty
	private String name;
	@NotEmpty
	private UserRole role;
	@Email
	private String email;
	@NotBlank
	private String phone;
	@NotBlank
	private UserStatus status;
	@NotBlank
	private  String password;
	

}
