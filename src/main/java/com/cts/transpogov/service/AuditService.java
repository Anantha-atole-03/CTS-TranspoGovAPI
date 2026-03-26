package com.cts.transpogov.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cts.transpogov.dtos.Audit.AuditResponse;
import com.cts.transpogov.dtos.Audit.CreateAuditRequest;
import com.cts.transpogov.dtos.Audit.GenerateReportResponse;
import com.cts.transpogov.dtos.Audit.UpdateAuditRequest;
import com.cts.transpogov.enums.AuditStatus;
import com.cts.transpogov.exceptions.AuditNotFoundException;
import com.cts.transpogov.models.Audit;
import com.cts.transpogov.models.User;
import com.cts.transpogov.repositories.AuditRepository;
import com.cts.transpogov.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditService implements IAuditService {

	private final UserRepository userRepository;

	private final AuditRepository auditRepository;

	/* ---------------- FIND ALL ---------------- */

	@Override
	public List<AuditResponse> findAll() {
		log.info("Fetching all audit records");
		List<AuditResponse> result = auditRepository.findAll().stream().map(this::toAuditResponse)
				.collect(Collectors.toList());
		log.info("Fetched {} audit records", result.size());
		return result;
	}

	/* ---------------- FIND BY ID ---------------- */

	@Override
	public AuditResponse findById(Long id) {
		log.info("Fetching audit by id: {}", id);
		Audit audit = auditRepository.findById(id).orElseThrow(() -> {
			log.warn("Audit not found for id: {}", id);
			return new AuditNotFoundException("Audit Record not found");
		});
		log.info("Fetched audit id: {}", audit.getId());
		return toAuditResponse(audit);
	}

	/* ---------------- CREATE ---------------- */

	@Override
	public AuditResponse create(CreateAuditRequest req) {
		log.info("Creating audit for officerId: {}", req.getOfficerId());

		Audit audit = new Audit();
		User officer = userRepository.findById(req.getOfficerId())
				.orElseThrow(() -> new RuntimeException("Compliance officer not found with id:" + req.getOfficerId()));
//		if(!officer.getRole().equals(UserRole.COMPLIANCE_OFFICER)){throw new RuntimeException("You have not permission, Access denied!")}
		audit.setOfficer(officer);
		audit.setScope(req.getScope());
		audit.setStatus(AuditStatus.OPEN);
		audit.setStartedAt(LocalDateTime.now());
		audit.setFindings(req.getFindings());
		Audit saved = auditRepository.save(audit);
		log.info("Audit created with id: {}", saved.getId());
		return toAuditResponse(saved);
	}

	/* ---------------- UPDATE ---------------- */

	@Override
	public AuditResponse update(Long id, UpdateAuditRequest req) {
		log.info("Updating audit id: {}", id);
		Audit existing = auditRepository.findById(id).orElseThrow(() -> {
			log.warn("Audit not found for update, id: {}", id);
			return new AuditNotFoundException("Audit Record not found");
		});
		existing.setScope(req.getScope());
		existing.setFindings(req.getFindings());
		if (req.getStatus() != null) {
			existing.setStatus(req.getStatus());
			if (req.getStatus() == AuditStatus.CLOSED) {
				existing.setClosedAt(LocalDateTime.now());
			}
		}
		Audit updatedAudit = auditRepository.save(existing);
		log.info("Audit updated successfully, id: {}", updatedAudit.getId());
		return toAuditResponse(updatedAudit);
	}
	/* ---------------- DELETE ---------------- */

	@Override
	public String delete(Long id) {
		log.info("Deleting audit record id: {}", id);
		Audit audit = auditRepository.findById(id).orElseThrow(() -> {
			log.warn("Audit not found for deletion, id: {}", id);
			return new AuditNotFoundException("Audit Record not found");
		});

		auditRepository.deleteById(audit.getId());
		log.info("Audit record deleted, id: {}", audit.getId());
		return "Record deleted Successfully";
	}

	/* ---------------- REPORT ---------------- */

	@Override
	public GenerateReportResponse generateReport(Long auditId) {
		log.info("Generating report for auditId: {}", auditId);
		Audit audit = auditRepository.findById(auditId)
				.orElseThrow(() -> new AuditNotFoundException("Audit not found"));
		String url = "https://reports.transpogov.local/audits/" + audit.getId() + "/report-"
				+ System.currentTimeMillis() + ".pdf";
//
//		audit.setReportUrl(url);
//		auditRepository.save(audit);

		GenerateReportResponse resp = new GenerateReportResponse();
		resp.setAuditId(audit.getId());
		resp.setReportUrl(url);
		resp.setGeneratedAt(LocalDateTime.now());
		log.info("Report generated for auditId: {}", auditId);
		return resp;
	}

	/* ---------------- COUNT ---------------- */
	@Override
	public Long getCount() {
		Long count = auditRepository.count();
		log.info("Audit records count: {}", count);
		return count;
	}

	@Override
	public Map<AuditStatus, Long> getStatusWiseCount() {
		log.info("Fetching audit count grouped by status");
		return auditRepository.findStatusCount().stream()
				.collect(Collectors.toMap(obj -> (AuditStatus) obj[0], obj -> (Long) obj[1]));
	}

	/* ---------------- HELPER ---------------- */

	private AuditResponse toAuditResponse(Audit audit) {
		AuditResponse dto = new AuditResponse();
		dto.setId(audit.getId());
		dto.setOfficerId(audit.getOfficer().getUserId());
		dto.setScope(audit.getScope());
		dto.setStatus(audit.getStatus());
		dto.setStartedAt(audit.getStartedAt());
		dto.setClosedAt(audit.getClosedAt());
//		dto.setReportUrl(audit.getReportUrl());
		dto.setFindings(audit.getFindings());
		return dto;
	}

	@Override
	public AuditResponse closeAudit(Long auditId) {
		log.info("Closing audit id: {}", auditId);

		Audit a = auditRepository.findById(auditId).orElseThrow(() -> {
			log.warn("Audit not found for closeAudit, id: {}", auditId);
			return new AuditNotFoundException("Audit not found: " + auditId);
		});
		if (a.getStatus().equals(AuditStatus.CLOSED))
			throw new RuntimeException(" Audit is Already Closed");
		a.setStatus(AuditStatus.CLOSED);
		a.setClosedAt(LocalDateTime.now());
		Audit saved = auditRepository.save(a);
		log.info("Audit closed id: {}, closedAt: {}", saved.getId(), saved.getClosedAt());
		return toAuditResponse(saved);
	}

}