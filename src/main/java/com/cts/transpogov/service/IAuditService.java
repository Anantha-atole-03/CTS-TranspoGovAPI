package com.cts.transpogov.service;

import com.cts.transpogov.dtos.Audit.AddFindingRequest;
import com.cts.transpogov.dtos.Audit.AuditFindingResponse;
import com.cts.transpogov.dtos.Audit.AuditResponse;
import com.cts.transpogov.dtos.Audit.CreateAuditRequest;
import com.cts.transpogov.dtos.Audit.GenerateReportResponse;
import com.cts.transpogov.dtos.Audit.PageResponse;
import com.cts.transpogov.dtos.Audit.UpdateAuditRequest;

public interface IAuditService {

	AuditResponse createAudit(CreateAuditRequest req);

	String delete(Long id);

	PageResponse<AuditResponse> listAudits(Integer page, Integer size, String sort, String status, Long officerId,
			String scopeContains);

	AuditResponse getAudit(Long id);

	AuditFindingResponse addFinding(Long auditId, AddFindingRequest req);

	GenerateReportResponse generateReport(Long auditId);

	AuditResponse closeAudit(Long auditId);

	AuditResponse update(Long id, UpdateAuditRequest req);
	

	Long getCount();

}