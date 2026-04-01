package com.cts.transpogov.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.transpogov.dtos.user.ForgotPasswordRequest;
import com.cts.transpogov.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	Optional<User> findByPhone(String phone);
	// void forgotPassword(ForgotPasswordRequest request);

	List<User> findAll();
}
