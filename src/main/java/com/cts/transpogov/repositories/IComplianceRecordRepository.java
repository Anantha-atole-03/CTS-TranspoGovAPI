package com.cts.transpogov.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.transpogov.enums.ComplianceResultStatus;
import com.cts.transpogov.models.ComplianceRecord;

public interface IComplianceRecordRepository extends JpaRepository<ComplianceRecord, Long> {
	long countByResult(ComplianceResultStatus result);
}

