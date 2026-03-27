package com.cts.transpogov.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.transpogov.dtos.ApiResponse;
import com.cts.transpogov.dtos.route.ScheduleCreateRequest;
import com.cts.transpogov.dtos.route.ScheduleResponse;
import com.cts.transpogov.dtos.route.ScheduleUpdateRequest;
import com.cts.transpogov.service.IScheduleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

	private final IScheduleService scheduleService;

	@PostMapping("/{routeId}")
	public ResponseEntity<ApiResponse<ScheduleResponse>> addSchedule(@PathVariable Long routeId,
			@RequestBody ScheduleCreateRequest schedule) {
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("New schedule created",
				HttpStatus.CREATED.value(), scheduleService.addSchedule(routeId, schedule)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<ScheduleResponse>> updateSchedule(@PathVariable Long id,
			@RequestBody ScheduleUpdateRequest schedule) {
		return ResponseEntity.ok(new ApiResponse<>("Schedule updated successfully", HttpStatus.OK.value(),
				scheduleService.updateSchedule(id, schedule)));
	}

	@GetMapping("/route/{routeId}")
	public ResponseEntity<ApiResponse<List<ScheduleResponse>>> getSchedulesByRoute(@PathVariable Long routeId) {
		return ResponseEntity.ok(new ApiResponse<>("Fetched schedules by route", HttpStatus.OK.value(),
				scheduleService.getSchedulesByRoute(routeId)));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<ScheduleResponse>> getScheduleById(@PathVariable Long id) {
		return ResponseEntity.ok(new ApiResponse<>("Fetched schedule by ID", HttpStatus.OK.value(),
				scheduleService.getScheduleById(id)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteSchedule(@PathVariable Long id) {
		scheduleService.deleteSchedule(id);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse<>("Schedule deleted successfully", HttpStatus.OK.value(), null));
	}
}
