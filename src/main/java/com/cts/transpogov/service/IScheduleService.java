package com.cts.transpogov.service;

import java.util.List;

import com.cts.transpogov.dtos.route.ScheduleCreateRequest;
import com.cts.transpogov.dtos.route.ScheduleResponse;
import com.cts.transpogov.dtos.route.ScheduleUpdateRequest;

public interface IScheduleService {
	ScheduleResponse addSchedule(Long routeId, ScheduleCreateRequest schedule);

	ScheduleResponse updateSchedule(Long id, ScheduleUpdateRequest schedule);

	List<ScheduleResponse> getSchedulesByRoute(Long routeId);

	ScheduleResponse getScheduleById(Long id);

	void deleteSchedule(Long id);
}
