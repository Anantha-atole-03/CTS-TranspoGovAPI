package com.cts.transpogov.dtos.program;

import java.time.LocalDate;

import com.cts.transpogov.enums.ProgramStatus;

import lombok.Data;

@Data
public class ProgramCreateRequest {
  private String title;
  private String description;
  private LocalDate startDate;
  private LocalDate endDate;
  private Double budget;
  private ProgramStatus status;
}
