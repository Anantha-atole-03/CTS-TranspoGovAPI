package com.cts.transpogov.dtos.audit;

import java.time.LocalDateTime;

import com.cts.transpogov.enums.AuditStatus;

import lombok.Data;

@Data
public class AuditResponse {

	private Long id;
	private Long officerId;
	private String scope;
	private AuditStatus status;
	private LocalDateTime startedAt;
	private LocalDateTime closedAt;
	private String reportUrl;
	private String findings;
}
