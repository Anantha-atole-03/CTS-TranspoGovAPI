package com.cts.transpogov.service;

import java.util.List;

import com.cts.transpogov.dtos.route.RouteCreateRequest;
import com.cts.transpogov.dtos.route.RouteResponse;
import com.cts.transpogov.dtos.route.RouteUpdateRequest;

public interface IRouteService {
	int countActiveRoutes();

	RouteResponse addRoute(RouteCreateRequest route);

	RouteResponse updateRoute(Long id, RouteUpdateRequest route);

	List<RouteResponse> getAllRoutes();

	RouteResponse getRouteById(Long id);

	void deleteRoute(Long id);

	List<RouteResponse> getRoutesByType(String type);
}
