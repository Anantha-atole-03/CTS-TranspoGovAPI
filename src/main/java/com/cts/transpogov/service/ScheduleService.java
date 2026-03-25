package com.cts.transpogov.service;

import com.transpogov.model.Schedule;
import java.util.List;

public interface ScheduleService {
    Schedule addSchedule(Long routeId, Schedule schedule);
    Schedule updateSchedule(Long id, Schedule schedule);
    List<Schedule> getSchedulesByRoute(Long routeId);
    Schedule getScheduleById(Long id);
    void deleteSchedule(Long id);
}


