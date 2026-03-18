package com.cts.transpogov.service;

import com.cts.transpogov.dtos.auth.LoginRequestDto;
import com.cts.transpogov.dtos.auth.LoginResponseDto;

public interface IAuthService {
	LoginResponseDto login(LoginRequestDto loginRequestDto);
}
