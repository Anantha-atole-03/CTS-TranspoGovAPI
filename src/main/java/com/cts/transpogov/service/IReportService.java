package com.cts.transpogov.service;

import java.util.Map;

import com.cts.transpogov.models.Report;

public interface IReportService {

	Map<String, Object> getOperationalDashboard();

	Report runCustomReport(String scope);

	Report getReportByJobId(Long jobId);
}
