package com.cts.transpogov.service;

import com.cts.transpogov.dtos.Audit.AddFindingRequest;
import com.cts.transpogov.dtos.Audit.AuditFindingResponse;
import com.cts.transpogov.dtos.Audit.AuditResponse;
import com.cts.transpogov.dtos.Audit.CreateAuditRequest;
import com.cts.transpogov.dtos.Audit.GenerateReportResponse;
import com.cts.transpogov.dtos.Audit.PageResponse;

public interface IAuditService {

	AuditResponse createAudit(CreateAuditRequest req);

	PageResponse<AuditResponse> listAudits(Integer page, Integer size, String sort, String status, Long officerId,
			String scopeContains);

	AuditResponse getAudit(Long id);

	AuditFindingResponse addFinding(Long auditId, AddFindingRequest req);

	GenerateReportResponse generateReport(Long auditId);

	AuditResponse closeAudit(Long auditId);
}