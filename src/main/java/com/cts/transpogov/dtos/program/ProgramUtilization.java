package com.cts.transpogov.dtos.program;

import java.time.LocalDate;

import com.cts.transpogov.enums.ProgramStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgramUtilization {

	// Program Details
	private Long programId;
	private String title;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private ProgramStatus status;

	// Financial Utilization
	private Double allocatedBudget;
	private Double utilizedBudget;
	private Double remainingBudget;
	private Double budgetUtilizationPercentage;

	private Integer totalResourcesAllocated;
	private Integer totalResourcesUsed;
}