package com.cts.transpogov.dto.auditlog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuditLogRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Action is required")
    @Size(max = 50, message = "Action must be <= 50 characters")
    private String action;

    @NotBlank(message = "Resource is required")
    @Size(max = 255, message = "Resource must be <= 255 characters")
    private String resource;
}