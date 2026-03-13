package com.cts.transpogov.dtos.route;

import java.time.LocalDate;
import java.time.LocalTime;

import com.cts.transpogov.enums.ScheduleStatus;

import lombok.Data;

@Data
class ScheduleCreateRequest {
  private Long routeId;
  private LocalDate date;
  private LocalTime time;
  private ScheduleStatus status;
}
