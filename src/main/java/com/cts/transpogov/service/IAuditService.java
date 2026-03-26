package com.cts.transpogov.service;

import java.util.List;
import java.util.Map;

import com.cts.transpogov.dtos.Audit.AuditResponse;
import com.cts.transpogov.dtos.Audit.CreateAuditRequest;
import com.cts.transpogov.dtos.Audit.GenerateReportResponse;
import com.cts.transpogov.dtos.Audit.UpdateAuditRequest;
import com.cts.transpogov.enums.AuditStatus;

public interface IAuditService {

	AuditResponse create(CreateAuditRequest req);

	String delete(Long id);

	AuditResponse findById(Long id);

	GenerateReportResponse generateReport(Long auditId);

	AuditResponse closeAudit(Long auditId);

	Map<AuditStatus, Long> getStatusWiseCount();

	AuditResponse update(Long id, UpdateAuditRequest req);

	Long getCount();

	List<AuditResponse> findAll();

}