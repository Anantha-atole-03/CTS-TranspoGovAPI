package com.cts.transpogov.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.transpogov.dtos.citizen.CitizenCreateRequest;
import com.cts.transpogov.dtos.citizen.CitizenResponse;
import com.cts.transpogov.enums.UserRole;
import com.cts.transpogov.exceptions.AuthenticationException;
import com.cts.transpogov.models.Citizen;
import com.cts.transpogov.repositories.CitizenRepository;
import com.cts.transpogov.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CitizenService implements ICitizenService {
	private final CitizenRepository citizenRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper;

	@Override
	public CitizenResponse save(CitizenCreateRequest requestDto) {
		Optional<Citizen> exits = citizenRepository.findByPhone(requestDto.getPhone());
		if (exits.isPresent() || userRepository.findByPhone(requestDto.getPhone()).isPresent())
			throw new AuthenticationException("Citizen alredy exists");
		Citizen user = modelMapper.map(requestDto, Citizen.class);
		user.setPhone(requestDto.getPhone());

		user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
		user.setRole(UserRole.CITIZEN_PASSENGER);
		Citizen user2 = citizenRepository.save(user);

		return modelMapper.map(user2, CitizenResponse.class);
	}

}
