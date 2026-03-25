package com.cts.transpogov.controllers;

import com.transpogov.model.Route;
import com.transpogov.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController  // converts JSON <-> Java objects
@RequestMapping("/api/routes")//sets the base URL for all endpoints in this controller
public class RouteController {

    @Autowired  //Dependency Injection
    private RouteService routeService;

    @PostMapping  // handles HTTP POST requests
    public ResponseEntity<Route> createRoute(@RequestBody Route route) {     //@RequestBody  extract values directly from the URL path 
        return ResponseEntity.ok(routeService.addRoute(route));
    }

    @PutMapping("/{id}") 
    public ResponseEntity<Route> updateRoute(@PathVariable Long id, @RequestBody Route route) {
        return ResponseEntity.ok(routeService.updateRoute(id, route));
    }

    @GetMapping
    public ResponseEntity<List<Route>> getAllRoutes() {
        return ResponseEntity.ok(routeService.getAllRoutes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Route> getRouteById(@PathVariable Long id) {
        return ResponseEntity.ok(routeService.getRouteById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        routeService.deleteRoute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Route>> getRoutesByType(@PathVariable String type) {
        return ResponseEntity.ok(routeService.getRoutesByType(type));
    }
}
