package com.cts.transpogov.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.transpogov.models.Citizen;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Long> {
	Optional<Citizen> findByPhone(String phone);
}
