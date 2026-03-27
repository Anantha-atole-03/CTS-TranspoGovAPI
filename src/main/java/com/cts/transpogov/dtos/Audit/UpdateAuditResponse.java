package com.cts.transpogov.dtos.Audit;

import java.time.LocalDateTime;

import com.cts.transpogov.enums.AuditStatus;

import lombok.Data;

@Data
public class UpdateAuditResponse {

	private Long auditId;
	private String scope;
	private AuditStatus status;
	private String findings;
	private String message;
	private LocalDateTime updatedAt;

}