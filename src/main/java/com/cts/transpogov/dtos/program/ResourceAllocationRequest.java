package com.cts.transpogov.dtos.program;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ResourceAllocationRequest {
  private Long programId;
  private Double quantity;
  private LocalDate effectiveFrom;
  private LocalDate effectiveTo;
  private String notes;
}
