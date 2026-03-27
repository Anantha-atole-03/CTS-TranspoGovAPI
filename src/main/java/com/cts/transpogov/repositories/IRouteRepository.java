package com.cts.transpogov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cts.transpogov.models.Route;
import com.cts.transpogov.enums.RouteStatus;

public interface IRouteRepository extends JpaRepository<Route, Long> {
    long countByStatus(RouteStatus status);   
}