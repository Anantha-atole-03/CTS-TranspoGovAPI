package com.cts.transpogov.controllers;

import com.cts.transpogov.dto.auditlog.AuditLogResponse;
import com.cts.transpogov.service.AuditLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

    private static final Logger log = LoggerFactory.getLogger(AuditLogController.class);
    private final AuditLogService service;

    public AuditLogController(AuditLogService service) {
        this.service = service;
    }

  
    @GetMapping("/{auditId}")
    public ResponseEntity<AuditLogResponse> getAuditLog(@PathVariable Long auditId) {
        log.info("Fetching audit log with ID: {}", auditId);
        return ResponseEntity.ok(service.get(auditId));
    }

   
    @GetMapping
    public ResponseEntity<List<AuditLogResponse>> getAllAuditLogs() {
        log.info("Fetching all audit logs");
        return ResponseEntity.ok(service.getAll());
    }
}