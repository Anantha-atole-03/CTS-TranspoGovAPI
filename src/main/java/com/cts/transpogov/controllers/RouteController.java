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
import com.cts.transpogov.dtos.route.RouteCreateRequest;
import com.cts.transpogov.dtos.route.RouteResponse;
import com.cts.transpogov.dtos.route.RouteUpdateRequest;
import com.cts.transpogov.service.IRouteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {

	private final IRouteService routeService;

	@PostMapping // handles HTTP POST requests
	public ResponseEntity<ApiResponse<RouteResponse>> createRoute(@RequestBody RouteCreateRequest route) {
		// @RequestBody extract values
		// from
		// the URL path
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<>("New route created", HttpStatus.CREATED.value(), routeService.addRoute(route)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<RouteResponse>> updateRoute(@PathVariable Long id,
			@RequestBody RouteUpdateRequest route) {
		return ResponseEntity.ok(new ApiResponse<>("Route updated successfully", HttpStatus.OK.value(),
				routeService.updateRoute(id, route)));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<RouteResponse>>> getAllRoutes() {
		return ResponseEntity
				.ok(new ApiResponse<>("Fetched all routes", HttpStatus.OK.value(), routeService.getAllRoutes()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<RouteResponse>> getRouteById(@PathVariable Long id) {
		return ResponseEntity
				.ok(new ApiResponse<>("Fetched route by ID", HttpStatus.OK.value(), routeService.getRouteById(id)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteRoute(@PathVariable Long id) {
		routeService.deleteRoute(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
				.body(new ApiResponse<>("Route deleted successfully", HttpStatus.NO_CONTENT.value(), null));
	}

	@GetMapping("/type/{type}")
	public ResponseEntity<ApiResponse<List<RouteResponse>>> getRoutesByType(@PathVariable String type) {
		return ResponseEntity.ok(
				new ApiResponse<>("Fetched routes by type", HttpStatus.OK.value(), routeService.getRoutesByType(type)));
	}
}
