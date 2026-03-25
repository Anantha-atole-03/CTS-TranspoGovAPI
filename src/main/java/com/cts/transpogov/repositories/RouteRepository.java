package com.cts.transpogov.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.transpogov.models.Route;

import java.util.List;

@Repository   //create and manage the object
public interface RouteRepository extends JpaRepository<Route, Long> {
    List<Route> findByType(String type);
    List<Route> findByStatus(String status);
}
