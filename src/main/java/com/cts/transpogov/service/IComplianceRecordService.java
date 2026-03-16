package com.cts.transpogov.service;

import java.util.List;

import com.cts.transpogov.dtos.compliance.ComplianceCreateRequest;
import com.cts.transpogov.dtos.compliance.ComplianceResponse;
import com.cts.transpogov.models.ComplianceRecord;

public interface IComplianceRecordService {
	List<ComplianceResponse> findAll();

	List<ComplianceResponse> findByEntityId(Long entityId);

	Long getCount();

	ComplianceResponse create(ComplianceCreateRequest record);

	ComplianceResponse update(Long id, ComplianceCreateRequest record);

	void delete(Long id);

	ComplianceResponse findById(Long id);

}