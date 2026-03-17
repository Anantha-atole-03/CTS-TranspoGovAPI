package com.cts.transpogov.service;

import com.cts.transpogov.dtos.Audit.*;
import com.cts.transpogov.enums.AuditStatus;
import com.cts.transpogov.exceptions.AuditNotFoundException;
import com.cts.transpogov.models.Audit;
import com.cts.transpogov.models.AuditFinding;
import com.cts.transpogov.repositories.AuditFindingRepository;
import com.cts.transpogov.repositories.AuditRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AuditService implements IAuditService {

	private final AuditRepository auditRepository;
	private final AuditFindingRepository findingRepository;

	@Override
	public AuditResponse createAudit(CreateAuditRequest req) {
		Audit a = new Audit();
		a.setOfficerId(req.getOfficerId());
		a.setScope(req.getScope());
		a.setStatus(AuditStatus.OPEN);
		a.setStartedAt(LocalDateTime.now());
		return toAuditResponse(auditRepository.save(a), true);
	}

	@Override
	public PageResponse<AuditResponse> listAudits(Integer page, Integer size, String sort, String status,
			Long officerId, String scopeContains) {

		Sort sortObj = parseSort(sort);
		int p = (page == null ? 0 : page);
		int s = (size == null ? 20 : size);
		Pageable pageable = PageRequest.of(p, s, sortObj);

		Page<Audit> paged = auditRepository.findAll(pageable);

		// simple in-memory filters; switch to JPA Specifications/QueryDSL later for
		// DB-side filtering
		var filtered = paged.getContent().stream()
				.filter(a -> status == null || a.getStatus().name().equalsIgnoreCase(status))
				.filter(a -> officerId == null || officerId.equals(a.getOfficerId()))
				.filter(a -> scopeContains == null
						|| (a.getScope() != null && a.getScope().toLowerCase().contains(scopeContains.toLowerCase())))
				.map(a -> toAuditResponse(a, false)) // omit findings in list
				.collect(Collectors.toList());

		PageResponse<AuditResponse> resp = new PageResponse<>();
		resp.setContent(filtered);
		resp.setTotalElements(paged.getTotalElements());
		resp.setTotalPages(paged.getTotalPages());
		resp.setPage(paged.getNumber());
		resp.setSize(paged.getSize());

		return resp;
	}

	@Override
	public AuditResponse getAudit(Long id) {
		Audit a = auditRepository.findById(id).orElseThrow(() -> new AuditNotFoundException("Audit not found: " + id));
		return toAuditResponse(a, true);
	}

	@Override
	public AuditFindingResponse addFinding(Long auditId, AddFindingRequest req) {
		Audit audit = auditRepository.findById(auditId)
				.orElseThrow(() -> new AuditNotFoundException("Audit not found: " + auditId));

		if (audit.getStatus() == AuditStatus.CLOSED) {
			throw new IllegalArgumentException("Cannot add finding to a CLOSED audit");
		}

		AuditFinding f = new AuditFinding();
		f.setAudit(audit);
		f.setTitle(req.getTitle());
		f.setDescription(req.getDescription());
		f.setSeverity(req.getSeverity());
		f.setStatus("OPEN");
		f.setCreatedAt(LocalDateTime.now());

		return toFindingResponse(findingRepository.save(f));
	}

	@Override
	public GenerateReportResponse generateReport(Long auditId) {
		Audit a = auditRepository.findById(auditId)
				.orElseThrow(() -> new AuditNotFoundException("Audit not found: " + auditId));

		String url = "https://reports.transpogov.local/audits/" + a.getId() + "/report-" + System.currentTimeMillis()
				+ ".pdf";
		a.setReportUrl(url);
		auditRepository.save(a);

		GenerateReportResponse resp = new GenerateReportResponse();
		resp.setAuditId(a.getId());
		resp.setReportUrl(url);
		resp.setGeneratedAt(LocalDateTime.now());
		return resp;
	}

	@Override
	public AuditResponse closeAudit(Long auditId) {
		Audit a = auditRepository.findById(auditId)
				.orElseThrow(() -> new AuditNotFoundException("Audit not found: " + auditId));

		boolean anyOpen = a.getFindings().stream().anyMatch(f -> !"RESOLVED".equalsIgnoreCase(f.getStatus()));
		if (anyOpen) {
			throw new IllegalArgumentException("Cannot close audit: unresolved findings exist");
		}

		a.setStatus(AuditStatus.CLOSED);
		a.setClosedAt(LocalDateTime.now());
		return toAuditResponse(auditRepository.save(a), true);
	}

	/* ----------------- helpers ----------------- */

	private Sort parseSort(String sort) {
		if (sort == null || sort.trim().isEmpty()) {
			return Sort.by(Sort.Direction.DESC, "startedAt");
		}
		String[] parts = sort.split("\\|");
		return Sort.by(java.util.Arrays.stream(parts).map(s -> {
			String[] p = s.split(",");
			return (p.length == 2 && "desc".equalsIgnoreCase(p[1])) ? new Sort.Order(Sort.Direction.DESC, p[0])
					: new Sort.Order(Sort.Direction.ASC, p[0]);
		}).collect(Collectors.toList()));
	}

	private AuditResponse toAuditResponse(Audit a, boolean includeFindings) {
		AuditResponse dto = new AuditResponse();
		dto.setId(a.getId());
		dto.setOfficerId(a.getOfficerId());
		dto.setScope(a.getScope());
		dto.setStatus(a.getStatus());
		dto.setStartedAt(a.getStartedAt());
		dto.setClosedAt(a.getClosedAt());
		dto.setReportUrl(a.getReportUrl());
		if (includeFindings && a.getFindings() != null) {
			dto.setFindings(a.getFindings().stream().map(this::toFindingResponse).collect(Collectors.toList()));
		}
		return dto;
	}

	private AuditFindingResponse toFindingResponse(AuditFinding f) {
		AuditFindingResponse dto = new AuditFindingResponse();
		dto.setId(f.getId());
		dto.setTitle(f.getTitle());
		dto.setDescription(f.getDescription());
		dto.setSeverity(f.getSeverity());
		dto.setStatus(f.getStatus());
		dto.setCreatedAt(f.getCreatedAt());
		return dto;
	}
}