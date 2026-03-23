package com.cts.transpogov.controllers;

import com.cts.transpogov.dtos.ApiResponse;
import com.cts.transpogov.dtos.Audit.AddFindingRequest;
import com.cts.transpogov.dtos.Audit.AuditFindingResponse;
import com.cts.transpogov.dtos.Audit.AuditResponse;
import com.cts.transpogov.dtos.Audit.CreateAuditRequest;
import com.cts.transpogov.dtos.Audit.GenerateReportResponse;
import com.cts.transpogov.dtos.Audit.PageResponse;
import com.cts.transpogov.dtos.Audit.UpdateAuditRequest;
import com.cts.transpogov.service.IAuditService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/audits")
public class AuditController {

    private final IAuditService auditService;

    
    public AuditController(IAuditService auditService) {
        this.auditService = auditService;
    }
    
    

    /* ========= POST /audits — Create audit ========= */
    @PostMapping("/save_audits")
    public ResponseEntity<ApiResponse<AuditResponse>> create(@Valid @RequestBody CreateAuditRequest req) {
        AuditResponse dto = auditService.createAudit(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("CREATED", HttpStatus.CREATED.value(), dto));
    }

    @DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
		String message = auditService.delete(id);
		return ResponseEntity.ok(new ApiResponse<>(message, HttpStatus.OK.value(), null));
	}
    
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<AuditResponse>> update(@PathVariable long id ,@RequestBody UpdateAuditRequest body)
    {
    	AuditResponse updated = auditService.update(id, body);
    	return ResponseEntity.ok(new ApiResponse<>("Record updated successfully", HttpStatus.OK.value(), updated));
    }
    
    
    
    
    /* ========= GET /audits — List audits (filters, pagination) ========= */
    @GetMapping("/audits_lists")
    public ResponseEntity<ApiResponse<PageResponse<AuditResponse>>> list(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long officerId,
            @RequestParam(required = false) String scope,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort
    ) {
    
        PageResponse<AuditResponse> resp =
                auditService.listAudits(page, size, sort, status, officerId, scope);
        return ResponseEntity.ok(new ApiResponse<>("Records fetched!", HttpStatus.OK.value(), resp));
    }

    /* ========= GET /audits/{id} — Audit details + findings ========= */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AuditResponse>> get(@PathVariable Long id) {
        AuditResponse dto = auditService.getAudit(id);
        return ResponseEntity.ok(new ApiResponse<>("Record fetched!", HttpStatus.OK.value(), dto));
    }

    /* ========= POST /audits/{id}/findings — Add finding ========= */
    @PostMapping("/{id}/findings")
    public ResponseEntity<ApiResponse<AuditFindingResponse>> addFinding(
            @PathVariable Long id, @Valid @RequestBody AddFindingRequest req) {
        AuditFindingResponse dto = auditService.addFinding(id, req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Finding created!", HttpStatus.CREATED.value(), dto));
    }

    /* ========= POST /audits/{id}/report — Generate report ========= */
    @PostMapping("/{id}/report")
    public ResponseEntity<ApiResponse<GenerateReportResponse>> generateReport(@PathVariable Long id) {
        GenerateReportResponse dto = auditService.generateReport(id);
        return ResponseEntity.ok(new ApiResponse<>("Report generated!", HttpStatus.OK.value(), dto));
    }

    /* ========= POST /audits/{id}/close — Close audit ========= */
    @PostMapping("/{id}/close")
    public ResponseEntity<ApiResponse<AuditResponse>> close(@PathVariable Long id) {
        AuditResponse dto = auditService.closeAudit(id);
        return ResponseEntity.ok(new ApiResponse<>("Audit closed!", HttpStatus.OK.value(), dto));
    }
    
	// GET /compliances/summary
	@GetMapping("/summary")
	public ResponseEntity<ApiResponse<Long>> getCount() {
		Long count = auditService.getCount();
		return ResponseEntity.ok(new ApiResponse<>("Count fetched!", HttpStatus.OK.value(), count));
	}
}