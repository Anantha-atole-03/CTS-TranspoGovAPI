package com.cts.transpogov.dto.auditlog;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AuditLogResponse {

    private Long auditId;
    private Long userId;
    private String action;
    private String resource;
    private LocalDateTime timestamp;
}