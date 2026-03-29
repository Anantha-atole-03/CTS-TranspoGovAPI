package com.cts.transpogov.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//happens if the token is expired?
//your JwtAuthenticationEntryPoint sends a JSON response with a 401 Unauthorized status and the message: 
//"JWT token is missing or invalid."
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	//handle the unauthorized access
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		response.getWriter().write("""
				    {
				      "error": "JWT token is missing or invalid"
				    }
				""");
	}
}