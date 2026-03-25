package com.cts.transpogov.controllers;

import com.transpogov.model.Schedule;
import com.transpogov.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/{routeId}")
    public ResponseEntity<Schedule> addSchedule(@PathVariable Long routeId, @RequestBody Schedule schedule) {
        return ResponseEntity.ok(scheduleService.addSchedule(routeId, schedule));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Long id, @RequestBody Schedule schedule) {
        return ResponseEntity.ok(scheduleService.updateSchedule(id, schedule));
    }

    @GetMapping("/route/{routeId}")
    public ResponseEntity<List<Schedule>> getSchedulesByRoute(@PathVariable Long routeId) {
        return ResponseEntity.ok(scheduleService.getSchedulesByRoute(routeId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.getScheduleById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
