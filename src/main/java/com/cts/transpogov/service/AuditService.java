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
import com.cts.transpogov.exceptions.AuditCloseNotFoundException;
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

	/**
	 * Retrieves all audit records from the database.
	 *
	 * @return list of {@link AuditResponse} representing all audits
	 * 
	 **/

	@Override
	public List<AuditResponse> findAll() {
		log.info("Fetching all audit records");
		List<AuditResponse> result = auditRepository.findAll().stream().map(this::toAuditResponse).toList();
		log.info("Fetched {} audit records", result.size());
		return result;
	}

	/**
	 * Fetches a single audit record by its unique identifier.
	 *
	 * @param id the audit ID
	 * @return {@link AuditResponse} containing audit details
	 * @throws AuditNotFoundException if the audit does not exist
	 */

	@Override
	public AuditResponse findById(Long id) {
		log.info("Fetching audit by id: {}", id);
		Audit audit = auditRepository.findById(id).orElseThrow(() -> {
			log.warn("Audit not found for id: {}", id);
			throw new AuditNotFoundException("Audit Record not found");
		});
		log.info("Fetched audit id: {}", audit.getId());
		return toAuditResponse(audit);
	}

	/**
	 * Creates a new audit record.
	 *
	 * The audit is initialized with OPEN status and the current timestamp.
	 *
	 * @param req the request containing audit creation details
	 * @return {@link AuditResponse} of the newly created audit
	 */

	@Override
	public AuditResponse create(CreateAuditRequest req) {
		log.info("Creating audit for officerId: {}", req.getOfficerId());

		Audit audit = new Audit();
		User officer = userRepository.findById(req.getOfficerId())
				.orElseThrow(() -> new RuntimeException("Compliance officer not found with id:" + req.getOfficerId()));
		audit.setOfficer(officer);
		audit.setScope(req.getScope());
		audit.setStatus(AuditStatus.OPEN);
		audit.setStartedAt(LocalDateTime.now());
		audit.setFindings(req.getFindings());
		Audit saved = auditRepository.save(audit);
		log.info("Audit created with id: {}", saved.getId());
		return toAuditResponse(saved);
	}

	/**
	 * Updates an existing audit record. Allows modification of scope, findings, and
	 * status. Automatically sets closure time if the status is changed to CLOSED.
	 * 
	 * @param id  the audit ID
	 * @param req the update request
	 * @return {@link AuditResponse} of the updated audit
	 * @throws AuditNotFoundException if the audit does not exist
	 */

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

	/**
	 * Deletes an audit record by its ID.
	 *
	 * @param id the audit ID
	 * @return confirmation message
	 * @throws AuditNotFoundException if the audit does not exist
	 */

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

	/**
	 * Generates a report for the specified audit.
	 *
	 * @param auditId the audit ID
	 * @return {@link GenerateReportResponse} containing report details
	 */

	@Override
	public GenerateReportResponse generateReport(Long auditId) {
		log.info("Generating report for auditId: {}", auditId);
		Audit audit = auditRepository.findById(auditId)
				.orElseThrow(() -> new AuditNotFoundException("Audit not found"));
		String url = "https://reports.transpogov.local/audits/" + audit.getId() + "/report-"
				+ System.currentTimeMillis() + ".pdf";

		GenerateReportResponse resp = new GenerateReportResponse();
		resp.setAuditId(audit.getId());
		resp.setReportUrl(url);
		resp.setGeneratedAt(LocalDateTime.now());
		log.info("Report generated for auditId: {}", auditId);
		return resp;
	}

	/**
	 * Returns the total count of audit records.
	 *
	 * @return total number of audits
	 */

	@Override
	public Long getCount() {
		Long count = auditRepository.count();
		log.info("Audit records count: {}", count);
		return count;
	}

	/**
	 * Retrieves audit counts grouped by their current status.
	 *
	 * @return map of {@link AuditStatus} to count
	 */

	@Override
	public Map<AuditStatus, Long> getStatusWiseCount() {
		log.info("Fetching audit count grouped by status");
		return auditRepository.findStatusCount().stream()
				.collect(Collectors.toMap(obj -> (AuditStatus) obj[0], obj -> (Long) obj[1]));
	}

	/**
	 * Closes an audit if it is not already closed.
	 *
	 * @param auditId the audit ID
	 * @return {@link AuditResponse} of the closed audit
	 * @throws AuditNotFoundException      if the audit does not exist
	 * @throws AuditCloseNotFoundException if the audit is already closed
	 */

	@Override
	public AuditResponse closeAudit(Long auditId) {
		log.info("Closing audit id: {}", auditId);

		Audit a = auditRepository.findById(auditId).orElseThrow(() -> {
			log.warn("Audit not found for closeAudit, id: {}", auditId);
			throw new AuditNotFoundException("Audit not found: " + auditId);
		});
		if (a.getStatus().equals(AuditStatus.CLOSED))
			throw new AuditCloseNotFoundException(" Audit is Already Closed");
		a.setStatus(AuditStatus.CLOSED);
		a.setClosedAt(LocalDateTime.now());
		Audit saved = auditRepository.save(a);
		log.info("Audit closed id: {}, closedAt: {}", saved.getId(), saved.getClosedAt());
		return toAuditResponse(saved);
	}

	/**
	 * Converts an {@link Audit} entity into an {@link AuditResponse} DTO.
	 *
	 * @param audit the audit entity
	 * @return mapped audit response DTO
	 */

	private AuditResponse toAuditResponse(Audit audit) {
		AuditResponse dto = new AuditResponse();
		dto.setId(audit.getId());
		dto.setOfficerId(audit.getOfficer().getUserId());
		dto.setScope(audit.getScope());
		dto.setStatus(audit.getStatus());
		dto.setStartedAt(audit.getStartedAt());
		dto.setClosedAt(audit.getClosedAt());
		dto.setFindings(audit.getFindings());
		return dto;
	}

}