package com.cts.transpogov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.transpogov.models.Citizen;

public interface CitizenDocumentRepository extends JpaRepository<Citizen, Long> {

}
