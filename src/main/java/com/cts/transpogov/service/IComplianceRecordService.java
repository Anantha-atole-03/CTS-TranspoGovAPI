package com.cts.transpogov.service;

import java.util.List;

import com.cts.transpogov.dtos.compliance.ComplianceCreateRequest;
import com.cts.transpogov.dtos.compliance.ComplianceResponse;

public interface IComplianceRecordService {
	List<ComplianceResponse> findAll();

	List<ComplianceResponse> findByEntityId(Long entityId);

	Long getCount();

	String create(ComplianceCreateRequest complianceCreateRequest);

	ComplianceResponse update(Long id, ComplianceCreateRequest complianceCreateRequest);

	String delete(Long id);

	ComplianceResponse findById(Long id);

	int getComplianceAlerts();

}
