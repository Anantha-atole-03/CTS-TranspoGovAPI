package com.cts.transpogov.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.transpogov.dtos.ApiResponse;
import com.cts.transpogov.dtos.Audit.AuditResponse;
import com.cts.transpogov.dtos.Audit.CreateAuditRequest;
import com.cts.transpogov.dtos.Audit.UpdateAuditRequest;
import com.cts.transpogov.service.IAuditService;

@RestController
@RequestMapping("/audits")
public class AuditController {

	private final IAuditService auditService;

	public AuditController(IAuditService auditService) {
		this.auditService = auditService;
	}

	/* ========= POST /audits — Create audit ========= */
	@PostMapping("/create")
	public ResponseEntity<AuditResponse> create(@RequestBody CreateAuditRequest request) {
		AuditResponse response = auditService.create(request);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
		String message = auditService.delete(id);
		return ResponseEntity.ok(new ApiResponse<>(message, HttpStatus.OK.value(), null));
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<ApiResponse<AuditResponse>> update(@PathVariable long id,
			@RequestBody UpdateAuditRequest body) {
		AuditResponse updated = auditService.update(id, body);
		return ResponseEntity.ok(new ApiResponse<>("Record updated successfully", HttpStatus.OK.value(), updated));
	}

	/* ========= GET /audits — List audits (filters, pagination) ========= */
	@GetMapping("/audits_lists")
	public ResponseEntity<List<AuditResponse>> findAll() {
		List<AuditResponse> audits = auditService.findAll();
		return ResponseEntity.ok(audits);
	}

	/* ========= GET /audits/{id} — Audit details + findings ========= */
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<AuditResponse>> get(@PathVariable Long id) {
		AuditResponse dto = auditService.findById(id);
		return ResponseEntity.ok(new ApiResponse<>("Record fetched!", HttpStatus.OK.value(), dto));
	}
//
//	/* ========= POST /audits/{id}/report — Generate report ========= */
//	@GetMapping("/{id}/report")
//	public ResponseEntity<ApiResponse<GenerateReportResponse>> generateReport(@PathVariable Long id) {
//		GenerateReportResponse dto = auditService.generateReport(id);
//		return ResponseEntity.ok(new ApiResponse<>("Report generated!", HttpStatus.OK.value(), dto));
//	}

	/* ========= POST /audits/{id}/close — Close audit ========= */
	@GetMapping("/{id}/close")
	public ResponseEntity<ApiResponse<AuditResponse>> close(@PathVariable Long id) {
		AuditResponse dto = auditService.closeAudit(id);
		return ResponseEntity.ok(new ApiResponse<>("Audit closed!", HttpStatus.OK.value(), dto));
	}

	// GET /compliances/summary
	@GetMapping("/summary")
	public ResponseEntity<ApiResponse<?>> getCount() {
		return ResponseEntity
				.ok(new ApiResponse<>("Count fetched!", HttpStatus.OK.value(), auditService.getStatusWiseCount()));
	}

}