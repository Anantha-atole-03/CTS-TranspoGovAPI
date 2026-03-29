package com.cts.transpogov.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.transpogov.models.Citizen;
import com.cts.transpogov.models.CitizenDocument;

public interface CitizenDocumentRepository extends JpaRepository<CitizenDocument, Long> {

	 List<CitizenDocument> findByCitizen_CitizenId(Long citizenId);

	CitizenDocument save(CitizenDocument document);
	
	

}
