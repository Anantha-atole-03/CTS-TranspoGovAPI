package com.cts.transpogov.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.transpogov.models.Audit;


public interface AuditRepository extends JpaRepository<Audit, Long> {
	
//	List<Audit> findByEntityId(Long entityId);
}
