package com.cts.transpogov.service;

import java.util.List;

import com.cts.transpogov.models.Route;

public interface RouteService {
    Route addRoute(Route route);
    Route updateRoute(Long id, Route route);
    List<Route> getAllRoutes();
    Route getRouteById(Long id);
    void deleteRoute(Long id);
    List<Route> getRoutesByType(String type);
}
