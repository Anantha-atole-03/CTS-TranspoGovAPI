package com.cts.transpogov.service;

import java.util.List;

import com.cts.transpogov.dtos.compliance.ComplianceCreateRequest;
import com.cts.transpogov.dtos.compliance.ComplianceResponse;

public interface IComplianceRecordService {
	List<ComplianceResponse> findAll();

	List<ComplianceResponse> findByEntityId(Long entityId);

	Long getCount();

	String create(ComplianceCreateRequest record);

	ComplianceResponse update(Long id, ComplianceCreateRequest record);

	String delete(Long id);

	ComplianceResponse findById(Long id);

//	Map<ComplianceResultStatus, Long> getStatusWiseCount();

}