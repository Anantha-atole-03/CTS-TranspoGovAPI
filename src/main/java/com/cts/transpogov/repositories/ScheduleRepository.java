package com.cts.transpogov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.transpogov.models.Schedule;

import java.util.List;

@Repository   //create and manage the object
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByRoute_RouteId(Long routeId);
    List<Schedule> findByStatus(String status);
}


