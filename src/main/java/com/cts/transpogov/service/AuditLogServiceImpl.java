package com.cts.transpogov.service;

import com.cts.transpogov.dto.auditlog.AuditLogResponse;
import com.cts.transpogov.models.AuditLog;
import com.cts.transpogov.repositories.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private static final Logger log = LoggerFactory.getLogger(AuditLogServiceImpl.class);
    private final AuditLogRepository repository;

    @Override
    public List<AuditLogResponse> getAll() {
        log.debug("Fetching all audit logs");

        List<AuditLogResponse> list = repository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        log.info("Total audit logs fetched: {}", list.size());
        return list;
    }

    @Override
    public AuditLogResponse get(Long auditId) {
        log.debug("Fetching audit log with ID: {}", auditId);

        AuditLog entity = repository.findById(auditId)
                .orElseThrow(() -> {
                    log.error("Audit log not found with ID: {}", auditId);
                    return new NoSuchElementException("Audit log not found: " + auditId);
                });

        return toResponse(entity);
    }

    private AuditLogResponse toResponse(AuditLog entity) {
        AuditLogResponse dto = new AuditLogResponse();
        dto.setAuditId(entity.getAuditId());
        dto.setUserId(entity.getUserId());
        dto.setAction(entity.getAction());
        dto.setResource(entity.getResource());
        dto.setTimestamp(entity.getTimestamp());
        return dto;
    }
}