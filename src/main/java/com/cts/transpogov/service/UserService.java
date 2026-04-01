package com.cts.transpogov.service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.transpogov.dtos.user.ForgotPasswordRequest;
import com.cts.transpogov.dtos.user.UserCreateRequest;
import com.cts.transpogov.dtos.user.UserResponse;
import com.cts.transpogov.exceptions.AuthenticationException;
import com.cts.transpogov.models.User;
import com.cts.transpogov.repositories.CitizenRepository;
import com.cts.transpogov.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for handling User-related business logic.
 * Manages user registration, unique credential generation, and data retrieval.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService {
	
	private final UserRepository userRepository;
	private final CitizenRepository citizenRepository;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper;

	/**
	 * Registers a new user after validating uniqueness and generating a secure password.
	 * * @param requestDto Data transfer object containing user registration details.
	 * @return UserResponse containing the saved user metadata.
	 * @throws AuthenticationException if the phone number is already registered in User or Citizen records.
	 */
	@Override
	public UserResponse save(UserCreateRequest requestDto) {
		// Check if the phone number already exists in either the User or Citizen repositories
		Optional<User> exits = userRepository.findByPhone(requestDto.getPhone());
		if (exits.isPresent() || citizenRepository.findByPhone(requestDto.getPhone()).isPresent()) {
			throw new AuthenticationException("User alredy exists");
		}

		// Map the request DTO to the User entity
		User user = modelMapper.map(requestDto, User.class);

		// Generate a temporary 6-digit numeric password
		String password = generateSixDigitPassword();
		
		// Securely encode the generated password before saving
		user.setPassword(passwordEncoder.encode(password));
		user.setRole(requestDto.getRole());
		
		// Persist the user to the database
		User user2 = userRepository.save(user);
		
		// Log the plain-text password (temporary measure for development/notification purposes)
		log.warn("Password: {} for user:{}", requestDto.getPhone(), password);
		
		return modelMapper.map(user2, UserResponse.class);
	}

	/**
	 * Retrieves all user accounts from the database.
	 * * @return List of User entities.
	 */
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	/**
	 * Generates a cryptographically secure 6-digit random number.
	 * Used as a default password for new registrations.
	 * * @return A 6-digit string ranging from 100000 to 999999.
	 */
	public static String generateSixDigitPassword() {
		SecureRandom random = new SecureRandom();

		// Ensures the result is always exactly 6 digits
		int number = 100000 + random.nextInt(900000);

		return String.valueOf(number);
	}
	
	@Override
	public void forgotPassword(ForgotPasswordRequest request) {

	    User user = userRepository.findByPhone(request.getPhone())
	            .orElseThrow(() -> new AuthenticationException("User not found"));

	    // Generate new temporary password
	    String tempPassword = generateSixDigitPassword();

	    // Encode and update password
	    user.setPassword(passwordEncoder.encode(tempPassword));
	    userRepository.save(user);

	    log.warn("Temporary Password for {} is {}", request.getPhone(), tempPassword);
	}
	

	 


}