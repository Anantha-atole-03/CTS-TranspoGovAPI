package com.cts.transpogov.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.transpogov.enums.RouteStatus;
import com.cts.transpogov.models.Route;

@Repository   //create and manage the object
public interface RouteRepository extends JpaRepository<Route, Long> {
    List<Route> findByType(String type);
    List<Route> findByStatus(String status);
	int countByStatus(RouteStatus active);
}
