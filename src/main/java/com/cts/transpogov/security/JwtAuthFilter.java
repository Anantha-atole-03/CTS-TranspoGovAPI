package com.cts.transpogov.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.cts.transpogov.models.Citizen;
import com.cts.transpogov.models.User;
import com.cts.transpogov.repositories.CitizenRepository;
import com.cts.transpogov.repositories.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

	private final UserRepository userRepository;
	private final CitizenRepository citizenRepository;
	private final AuthUtils authUtils;
	private final HandlerExceptionResolver handlerExceptionResolver;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			log.info("Incoming request: {}", request.getRequestURI());

			final String authHeader = request.getHeader("Authorization");

			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				filterChain.doFilter(request, response);
				return;
			}

			String token = authHeader.substring(7);
			String phone = authUtils.getUsername(token);

			if (phone != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				Optional<User> userOpt = userRepository.findByPhone(phone);

				if (userOpt.isPresent()) {
					User user = userOpt.get();

					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null,
							user.getAuthorities());

					SecurityContextHolder.getContext().setAuthentication(auth);

				} else {
					Citizen citizen = citizenRepository.findByPhone(phone)
							.orElseThrow(() -> new UsernameNotFoundException("No user found with phone: " + phone));

					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(citizen, null,
							citizen.getAuthorities());

					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			}

			filterChain.doFilter(request, response);

		} catch (Exception ex) {
			handlerExceptionResolver.resolveException(request, response, null, ex);
			return;
		}
	}
}