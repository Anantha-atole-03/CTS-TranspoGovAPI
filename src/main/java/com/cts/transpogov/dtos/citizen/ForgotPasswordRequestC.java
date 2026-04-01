package com.cts.transpogov.dtos.citizen;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordRequestC {

	private String phone;
	private String newPassword;

}
