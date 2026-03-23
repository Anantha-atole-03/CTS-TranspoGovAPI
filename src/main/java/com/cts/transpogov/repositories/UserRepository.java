package com.cts.transpogov.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.transpogov.models.CitizenDocument;
import com.cts.transpogov.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//	public User findByEmail(String email);
//	public User findByPhone(String phone);

	Optional<User> findByEmail(String email);

	Optional<User> findByPhone(String phone);
	
	List<User> findAll();

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByPhone(String phone);
}
