package com.cts.transpogov.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cts.transpogov.dtos.citizen.CitizenCreateRequest;
import com.cts.transpogov.dtos.citizen.CitizenResponse;
import com.cts.transpogov.dtos.citizen.CitizenUpdateRequest;
import com.cts.transpogov.enums.UserRole;
import com.cts.transpogov.exceptions.AuthenticationException;
import com.cts.transpogov.exceptions.ResourceNotFoundException; 
import com.cts.transpogov.exceptions.UserAlreadyExistsExcetions;
import com.cts.transpogov.models.Citizen;
import com.cts.transpogov.repositories.CitizenRepository;
import com.cts.transpogov.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor 
@Slf4j
@Transactional
public class CitizenServiceImpl implements ICitizenService {

    private final CitizenRepository citizenRepository;
    private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper;
    private final ModelMapper mapper;

    @Override
    public CitizenResponse addCitizen(CitizenCreateRequest request) {
        log.info("Registering new citizen with phone: {}", request.getPhone());
        
        citizenRepository.findByPhone(request.getPhone()).ifPresent(c -> {
            throw new UserAlreadyExistsExcetions("Account with phone " + request.getPhone() + " already exists.");
        });

        Citizen citizen = mapper.map(request, Citizen.class);
        Citizen savedCitizen = citizenRepository.save(citizen);
        
        return mapper.map(savedCitizen, CitizenResponse.class);
    }

    @Override
    public CitizenResponse updateCitizen(Long id, CitizenUpdateRequest request) {
        log.info("Updating citizen ID: {}", id);
        
        Citizen existingCitizen = citizenRepository.findById(id)
                .orElseThrow();

       
        mapper.map(request, existingCitizen);
        
        Citizen updatedCitizen = citizenRepository.save(existingCitizen);
        return mapper.map(updatedCitizen, CitizenResponse.class);
    }

    @Override
    public CitizenResponse getCitizenById(Long id) {
        Citizen citizen = citizenRepository.findById(id)
                .orElseThrow();
        return mapper.map(citizen, CitizenResponse.class);
    }

    @Override
    public List<CitizenResponse> getAll() {
        return citizenRepository.findAll()
                .stream()
                .map(citizen -> mapper.map(citizen, CitizenResponse.class))
                .collect(Collectors.toList());
    }

 

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