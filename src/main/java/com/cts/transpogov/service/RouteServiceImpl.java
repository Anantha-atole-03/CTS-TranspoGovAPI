package com.cts.transpogov.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.transpogov.exceptions.RouteNotFoundException;
import com.cts.transpogov.models.Route;
import com.cts.transpogov.repositories.RouteRepository;
import java.util.List;

// Service implementation for managing Routes.
// Handles business logic and interacts with the repository layer.
// Loggers write messages when CRUD operations perform (like adding, updating, or deleting a route).

@Service // tells Spring this is a service layer bean.
public class RouteServiceImpl implements RouteService {

    private static final Logger logger = LoggerFactory.getLogger(RouteServiceImpl.class);

    @Autowired
    private RouteRepository routeRepository;

    @Override // Method: Add Route
    public Route addRoute(Route route) {
        logger.info("Adding new route: {}", route.getTitle());
        return routeRepository.save(route);
    }

    @Override // Method: Update Route
    public Route updateRoute(Long id, Route route) {
        logger.info("Updating route with id: {}", id);
        Route existing = routeRepository.findById(id)
                .orElseThrow(() -> new RouteNotFoundException("Route not found with id: " + id));

        existing.setTitle(route.getTitle());
        existing.setType(route.getType());
        existing.setStartPoint(route.getStartPoint());
        existing.setEndPoint(route.getEndPoint());
        existing.setStatus(route.getStatus());

        Route updated = routeRepository.save(existing);
        logger.info("Route updated successfully with id: {}", updated.getRouteId());
        return updated;
    }

    @Override // Method: Get All Routes
    public List<Route> getAllRoutes() {
        logger.info("Fetching all routes");
        return routeRepository.findAll();
    }

    @Override // Method: Get Route by ID
    public Route getRouteById(Long id) {
        logger.info("Fetching route with id: {}", id);
        return routeRepository.findById(id)
                .orElseThrow(() -> new RouteNotFoundException("Route not found with id: " + id));
    }

    @Override // Method: Delete Route
    public void deleteRoute(Long id) {
        logger.info("Deleting route with id: {}", id);
        if (!routeRepository.existsById(id)) {
            throw new RouteNotFoundException("Route not found with id: " + id);
        }
        routeRepository.deleteById(id);
        logger.info("Route deleted successfully with id: {}", id);
    }

    @Override // Method: Get Routes by Type
    public List<Route> getRoutesByType(String type) {
        logger.info("Fetching routes by type: {}", type);
        return routeRepository.findByType(type);
    }
}
