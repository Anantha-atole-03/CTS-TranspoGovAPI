package com.cts.transpogov.dtos.program;

import java.time.LocalDate;

import com.cts.transpogov.enums.ProgramStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProgramResponse {
  private Long programId;
  private String title;
  private String description;
  private LocalDate startDate;
  private LocalDate endDate;
  private Double budget;
  private ProgramStatus status;
}
