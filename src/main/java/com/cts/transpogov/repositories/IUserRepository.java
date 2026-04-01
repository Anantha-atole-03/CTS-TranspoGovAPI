package com.cts.transpogov.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.transpogov.models.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

}
