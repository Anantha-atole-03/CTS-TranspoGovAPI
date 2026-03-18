package com.cts.transpogov.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.cts.transpogov.dtos.citizen.CitizenCreateRequest;
import com.cts.transpogov.exceptions.UserAlreadyExistsExcetions;
import com.cts.transpogov.models.Citizen;
import com.cts.transpogov.repositories.CitizenRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor 
@Slf4j
public class CitizenServiceImpl implements ICitizenService {

	private final CitizenRepository citizenRepository;
	private final ModelMapper mapper;
	@Override
	public String addCitizen(CitizenCreateRequest citizenCreateRequest) {
		
		Optional<Citizen> citizenDb=citizenRepository.findByPhone(citizenCreateRequest.getPhone());
		if(citizenDb.isPresent()) {
			throw new UserAlreadyExistsExcetions("Account with given credentials already exists try with login");
		}
		Citizen citizen=mapper.map(citizenCreateRequest, Citizen.class);
		log.info(citizen.toString());
		citizenRepository.save(citizen);
		
		return "Registration success ";
	}

	
}
