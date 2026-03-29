package com.cts.transpogov.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.transpogov.models.User;
import com.cts.transpogov.repositories.CitizenRepository;
import com.cts.transpogov.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
//conntects the database with spring security
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;
	private final CitizenRepository citizenRepository;

	//db data convert into userdetails
	//authentication process
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByPhone(username);
		if (user.isPresent()) {
			return user.get();
		}

		return citizenRepository.findByPhone(username)
				.orElseThrow(() -> new UsernameNotFoundException("Invalid phone number or password"));
	}

}
