package com.cts.transpogov.dtos.Audit;

import com.cts.transpogov.enums.AuditStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateAuditRequest {

	@NotBlank(message = "Scope is required")
	private String scope;

	@NotNull(message = "Status is required")
	private AuditStatus status;

	@NotBlank(message = "Findings is required")
	private String findings;
}