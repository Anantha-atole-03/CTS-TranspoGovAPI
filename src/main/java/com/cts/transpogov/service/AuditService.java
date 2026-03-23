package com.cts.transpogov.service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cts.transpogov.dtos.Audit.AddFindingRequest;
import com.cts.transpogov.dtos.Audit.AuditFindingResponse;
import com.cts.transpogov.dtos.Audit.AuditResponse;
import com.cts.transpogov.dtos.Audit.CreateAuditRequest;
import com.cts.transpogov.dtos.Audit.GenerateReportResponse;
import com.cts.transpogov.dtos.Audit.PageResponse;
import com.cts.transpogov.dtos.Audit.UpdateAuditRequest;
import com.cts.transpogov.enums.AuditStatus;
import com.cts.transpogov.exceptions.AuditNotFoundException;
import com.cts.transpogov.models.Audit;
import com.cts.transpogov.models.AuditFinding;
import com.cts.transpogov.repositories.AuditFindingRepository;
import com.cts.transpogov.repositories.AuditRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuditService implements IAuditService {

	private final AuditRepository auditRepository;
	private final AuditFindingRepository findingRepository;

	@Override
	public AuditResponse createAudit(CreateAuditRequest req) {
		log.info("Creating audit for officerId: {}, scope: {}", req.getOfficerId(), req.getScope());
		Audit a = new Audit();
		a.setOfficerId(req.getOfficerId());
		a.setScope(req.getScope());
		a.setStatus(AuditStatus.OPEN);
		a.setStartedAt(LocalDateTime.now());

		Audit saved = auditRepository.save(a);
		log.info("Audit created with id: {}, status: {}", saved.getId(), saved.getStatus());
		return toAuditResponse(saved, true);
	}

	@Override
	public PageResponse<AuditResponse> listAudits(Integer page, Integer size, String sort, String status,
			Long officerId, String scopeContains) {

		Sort sortObj = parseSort(sort);
		int p = (page == null ? 0 : page);
		int s = (size == null ? 20 : size);
		Pageable pageable = PageRequest.of(p, s, sortObj);

		log.info("Listing audits page={}, size={}, sort={}, filters: status={}, officerId={}, scopeContains={}", p, s,
				sort, status, officerId, scopeContains);

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

		log.info("Audits listed: returned={}, totalElements={}, totalPages={}, page={}, size={}", filtered.size(),
				paged.getTotalElements(), paged.getTotalPages(), paged.getNumber(), paged.getSize());

		return resp;
	}

	@Override
	public AuditResponse getAudit(Long id) {
		log.info("Fetching audit by id: {}", id);
		Audit a = auditRepository.findById(id).orElseThrow(() -> {
			log.warn("Audit not found for id: {}", id);
			return new AuditNotFoundException("Audit not found: " + id);
		});
		log.info("Audit fetched id: {}, status: {}", a.getId(), a.getStatus());
		return toAuditResponse(a, true);
	}

	@Override
	public AuditFindingResponse addFinding(Long auditId, AddFindingRequest req) {
		log.info("Adding finding to auditId: {}, title: {}", auditId, req.getTitle());
		Audit audit = auditRepository.findById(auditId).orElseThrow(() -> {
			log.warn("Audit not found for addFinding, id: {}", auditId);
			return new AuditNotFoundException("Audit not found: " + auditId);
		});

		if (audit.getStatus() == AuditStatus.CLOSED) {
			log.warn("Attempt to add finding to CLOSED audit, id: {}", auditId);
			throw new IllegalArgumentException("Cannot add finding to a CLOSED audit");
		}

		AuditFinding f = new AuditFinding();
		f.setAudit(audit);
		f.setTitle(req.getTitle());
		f.setDescription(req.getDescription());
		f.setSeverity(req.getSeverity());
		f.setStatus("OPEN");
		f.setCreatedAt(LocalDateTime.now());

		AuditFinding saved = findingRepository.save(f);
		log.info("Finding added id: {} to auditId: {}", saved.getId(), auditId);
		return toFindingResponse(saved);
	}

	@Override
	public GenerateReportResponse generateReport(Long auditId) {
		log.info("Generating report for auditId: {}", auditId);
		Audit a = auditRepository.findById(auditId).orElseThrow(() -> {
			log.warn("Audit not found for generateReport, id: {}", auditId);
			return new AuditNotFoundException("Audit not found: " + auditId);
		});

		String url = "https://reports.transpogov.local/audits/" + a.getId() + "/report-" + System.currentTimeMillis()
				+ ".pdf";
		a.setReportUrl(url);
		auditRepository.save(a);

		GenerateReportResponse resp = new GenerateReportResponse();
		resp.setAuditId(a.getId());
		resp.setReportUrl(url);
		resp.setGeneratedAt(LocalDateTime.now());

		log.info("Report generated for auditId: {}, url: {}", a.getId(), url);
		return resp;
	}

	@Override
	public AuditResponse closeAudit(Long auditId) {
		log.info("Closing audit id: {}", auditId);
		Audit a = auditRepository.findById(auditId).orElseThrow(() -> {
			log.warn("Audit not found for closeAudit, id: {}", auditId);
			return new AuditNotFoundException("Audit not found: " + auditId);
		});

		boolean anyOpen = a.getFindings().stream().anyMatch(f -> !"RESOLVED".equalsIgnoreCase(f.getStatus()));
		if (anyOpen) {
			log.warn("Cannot close audit id: {} — unresolved findings exist", auditId);
			throw new IllegalArgumentException("Cannot close audit: unresolved findings exist");
		}

		a.setStatus(AuditStatus.CLOSED);
		a.setClosedAt(LocalDateTime.now());
		Audit saved = auditRepository.save(a);

		log.info("Audit closed id: {}, closedAt: {}", saved.getId(), saved.getClosedAt());
		return toAuditResponse(saved, true);
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

	@Override
	public String delete(Long id) {
		log.info("Deleting udit record id: {}", id);
		Audit audit = auditRepository.findById(id).orElseThrow(() -> {
			log.warn("Compliance record not found for deletion, id: {}", id);
			return new AuditNotFoundException("Audit Record not found");
		});
//		auditRepository.deleteById(audit.getId());
		audit.setStatus(AuditStatus.DELETED);
		auditRepository.save(audit);
		log.info("Compliance record deleted, id: {}", audit.getId());
		return "Record deleted Succesfully";
	}

	@Override
	public AuditResponse update(Long id, UpdateAuditRequest req) {
	    log.info("Updating audit record id: {}", id);

	    Audit existing = auditRepository.findById(id)
	            .orElseThrow(() -> {
	                log.warn("Audit record not found for update, id: {}", id);
	                return new AuditNotFoundException("Audit Record not found");
	            });

	    // Update allowed fields
	   
	    existing.setScope(req.getScope());

	    // Optional: allow status update only if provided
	    if (req.getStatus() != null) {
	        existing.setStatus(req.getStatus());
	    }

	    Audit saved = auditRepository.save(existing);
	    log.info("Audit record updated, id: {}", saved.getId());

	    return toAuditResponse(saved, true);
	}

	  @Override
	    public Long getCount() {
	        Long count = auditRepository.count();
	        log.info("Audits records count: {}", count);
	        return count;
	    }
}
