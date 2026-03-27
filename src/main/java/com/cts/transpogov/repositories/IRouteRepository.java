package com.cts.transpogov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.transpogov.enums.RouteStatus;
import com.cts.transpogov.models.Route;

@Repository
public interface IRouteRepository extends JpaRepository<Route, Long> {
	long countByStatus(RouteStatus status);
}