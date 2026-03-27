package com.cts.transpogov.dtos.report;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DashboardResponse {
    private int activeRoutes;
    private long totalTickets;
    private int complianceAlerts;
    private double programEfficiency;
}