package com.cts.transpogov.dtos.route;

import java.time.LocalDate;
import java.time.LocalTime;

import com.cts.transpogov.enums.ScheduleStatus;

import lombok.Data;

@Data
public class ScheduleResponse {
	private Long scheduleId;
	private Long routeId;
	private LocalDate date;
	private LocalTime time;
	private ScheduleStatus status;
}
