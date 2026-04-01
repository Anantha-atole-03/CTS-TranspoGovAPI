package com.cts.transpogov.service;

import com.cts.transpogov.dto.auditlog.AuditLogResponse;
import java.util.List;

public interface AuditLogService {

    List<AuditLogResponse> getAll();

    AuditLogResponse get(Long auditId);
}
