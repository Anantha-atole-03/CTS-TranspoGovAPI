package com.cts.transpogov.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cts.transpogov.models.Citizen;
import com.cts.transpogov.models.User;

public interface ICitizenRepository extends JpaRepository<Citizen, Long> {

	Optional<Citizen> findByEmail(String email);


}
