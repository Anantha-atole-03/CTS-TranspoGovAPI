package com.cts.transpogov.dtos.compliance;

import java.time.LocalDate;

import com.cts.transpogov.enums.ComplianceResultStatus;
import com.cts.transpogov.enums.EntityType;

import lombok.Data;

@Data
public class ComplianceCreateRequest {

	private LocalDate complianceDate;
	private Long entityId;
	private String notes;
	private ComplianceResultStatus result;
	private EntityType type;

}
