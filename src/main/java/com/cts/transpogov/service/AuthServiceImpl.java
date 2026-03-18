package com.cts.transpogov.service;

import org.springframework.stereotype.Service;

import com.cts.transpogov.dtos.auth.LoginRequestDto;
import com.cts.transpogov.dtos.auth.LoginResponseDto;
import com.cts.transpogov.enums.UserRole;
import com.cts.transpogov.exceptions.InvalidUsernamePasswordException;
import com.cts.transpogov.models.Citizen;
import com.cts.transpogov.models.User;
import com.cts.transpogov.repositories.CitizenRepository;
import com.cts.transpogov.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements IAuthService {
	private final UserRepository repository;
	private final CitizenRepository citizenRepository;
	@Override
	public LoginResponseDto login(LoginRequestDto loginRequestDto) {
		if (UserRole.CITIZEN_PASSENGER.equals(loginRequestDto.getRole())) {
			Citizen user = citizenRepository.findByPhone(loginRequestDto.getPhone()).orElseThrow(()->new InvalidUsernamePasswordException("Invalid username"));
			if (user.getPassword().equals(loginRequestDto.getPassword())) {
				log.info(user.toString());
				LoginResponseDto dto = new LoginResponseDto();
				dto.setPhone(user.getPhone());
				dto.setRole(loginRequestDto.getRole());
				dto.setToken("abcd token");
				return dto;
			} else {
				throw new InvalidUsernamePasswordException("Invalid password!");
			}
		} else {
			User user = repository.findByPhone(loginRequestDto.getPhone()).orElseThrow(()->new InvalidUsernamePasswordException("Invalid username"));
			if (user.getPassword().equals(loginRequestDto.getPassword())) {
				log.info(user.toString());
				LoginResponseDto dto = new LoginResponseDto();
				dto.setPhone(user.getPhone());
				dto.setRole(user.getRole());
				dto.setToken("abcd token");
				return dto;
			} else {
				throw new InvalidUsernamePasswordException("Invalid password!");
			}
		}

	}

}
