package com.cts.transpogov.service;

import com.transpogov.exception.RouteNotFoundException;
import com.transpogov.exception.ScheduleNotFoundException;
import com.transpogov.model.Route;
import com.transpogov.model.Schedule;
import com.transpogov.repositories.RouteRepository;
import com.transpogov.repositories.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

//Service implementation for managing Schedules.
// Handles business logic and interacts with the repository layer.
 
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Override
    public Schedule addSchedule(Long routeId, Schedule schedule) {
        logger.info("Adding schedule for routeId: {}", routeId);
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RouteNotFoundException("Route not found with id: " + routeId));
        schedule.setRoute(route);
        Schedule saved = scheduleRepository.save(schedule);
        logger.info("Schedule added successfully with id: {}", saved.getScheduleId());
        return saved;
    }

    @Override
    public Schedule updateSchedule(Long id, Schedule schedule) {
        logger.info("Updating schedule with id: {}", id);
        Schedule existing = scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule not found with id: " + id));

        existing.setDate(schedule.getDate());
        existing.setTime(schedule.getTime());
        existing.setStatus(schedule.getStatus());

        Schedule updated = scheduleRepository.save(existing);
        logger.info("Schedule updated successfully with id: {}", updated.getScheduleId());
        return updated;
    }

    @Override
    public List<Schedule> getSchedulesByRoute(Long routeId) {
        logger.info("Fetching schedules for routeId: {}", routeId);
        return scheduleRepository.findByRoute_RouteId(routeId);
    }

    @Override
    public Schedule getScheduleById(Long id) {
        logger.info("Fetching schedule with id: {}", id);
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule not found with id: " + id));
    }

    @Override
    public void deleteSchedule(Long id) {
        logger.info("Deleting schedule with id: {}", id);
        if (!scheduleRepository.existsById(id)) {
            throw new ScheduleNotFoundException("Schedule not found with id: " + id);
        }
        scheduleRepository.deleteById(id);
        logger.info("Schedule deleted successfully with id: {}", id);
    }
}
