package com.cts.transpogov.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.cts.transpogov.enums.UserRole;
import com.cts.transpogov.models.Citizen;
import com.cts.transpogov.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class AuthUtils {

	@Value("${jwt.secret}")
	private String jwtSecret;

	private SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
	}

	public String generateAccessToken(UserDetails userDetails) {

		String phone;
		UserRole role;
		String id;

		if (userDetails instanceof User user) {
			phone = user.getPhone();
			role = user.getRole();
			id = user.getUserId().toString();

		} else if (userDetails instanceof Citizen citizen) {
			phone = citizen.getPhone();
			role = citizen.getRole();
			id = citizen.getCitizenId().toString();

		} else {
			throw new IllegalArgumentException("Unsupported UserDetails type");
		}

		return Jwts.builder().setSubject(phone).claim("id", id).claim("role", role).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + (5 * 60 * 60 * 1000))).signWith(getSecretKey())
				.compact();
	}

	private Claims getClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token).getBody();
	}

	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}

	public String getRole(String token) {
		return getClaims(token).get("role", String.class);
	}

}