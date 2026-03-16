package com.cts.transpogov.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.transpogov.models.ComplianceRecord;

@Repository
public interface ComplianceRecordRepository extends JpaRepository<ComplianceRecord, Long> {

	List<ComplianceRecord> findByEntityId(Long entityId);
    // Basic CRUD is enough for now
}