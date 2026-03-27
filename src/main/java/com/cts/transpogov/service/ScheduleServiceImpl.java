package com.cts.transpogov.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cts.transpogov.dtos.route.ScheduleCreateRequest;
import com.cts.transpogov.dtos.route.ScheduleResponse;
import com.cts.transpogov.dtos.route.ScheduleUpdateRequest;
import com.cts.transpogov.exceptions.RouteNotFoundException;
import com.cts.transpogov.exceptions.ScheduleNotFoundException;
import com.cts.transpogov.models.Route;
import com.cts.transpogov.models.Schedule;
import com.cts.transpogov.repositories.RouteRepository;
import com.cts.transpogov.repositories.ScheduleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

//Service implementation for managing Schedules.
// Handles business logic and interacts with the repository layer.

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleServiceImpl implements IScheduleService {

	private static final Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);

	private final ScheduleRepository scheduleRepository;
	private final RouteRepository routeRepository;
	private final ModelMapper modelMapper;

	@Override
	public ScheduleResponse addSchedule(Long routeId, ScheduleCreateRequest scheduleRequest) {
		logger.info("Adding schedule for routeId: {}", routeId);
		Route route = routeRepository.findById(routeId)
				.orElseThrow(() -> new RouteNotFoundException("Route not found with id: " + routeId));

		Schedule schedule = modelMapper.map(scheduleRequest, Schedule.class);
		schedule.setRoute(route);

		Schedule saved = scheduleRepository.save(schedule);
		logger.info("Schedule added successfully with id: {}", saved.getScheduleId());
		ScheduleResponse response = modelMapper.map(saved, ScheduleResponse.class);
		response.setRouteId(saved.getRoute().getRouteId());
		return response;
	}

	@Override
	public ScheduleResponse updateSchedule(Long id, ScheduleUpdateRequest scheduleRequest) {
		logger.info("Updating schedule with id: {}", id);
		Schedule existing = scheduleRepository.findById(id)
				.orElseThrow(() -> new ScheduleNotFoundException("Schedule not found with id: " + id));

		existing.setDate(scheduleRequest.getDate());
		existing.setTime(scheduleRequest.getTime());
		existing.setStatus(scheduleRequest.getStatus());

		Schedule updated = scheduleRepository.save(existing);
		logger.info("Schedule updated successfully with id: {}", updated.getScheduleId());
		return modelMapper.map(updated, ScheduleResponse.class);
	}

	@Override
	public List<ScheduleResponse> getSchedulesByRoute(Long routeId) {
		logger.info("Fetching schedules for routeId: {}", routeId);
		return scheduleRepository.findByRoute_RouteId(routeId).stream()
				.map(schedule -> modelMapper.map(schedule, ScheduleResponse.class)).toList();
	}

	@Override
	public ScheduleResponse getScheduleById(Long id) {
		logger.info("Fetching schedule with id: {}", id);
		Schedule schedule = scheduleRepository.findById(id)
				.orElseThrow(() -> new ScheduleNotFoundException("Schedule not found with id: " + id));
		return modelMapper.map(schedule, ScheduleResponse.class);
	}

	@Override
	public void deleteSchedule(Long id) {
		logger.info("Deleting schedule with id: {}", id);
		scheduleRepository.findById(id)
				.orElseThrow(() -> new ScheduleNotFoundException("Schedule not found with id: " + id));

		scheduleRepository.deleteById(id);
		logger.info("Schedule deleted successfully with id: {}", id);
		return;
	}
}
