package com.cts.transpogov.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cts.transpogov.models.Report;
import com.cts.transpogov.repositories.IReportRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReportService implements IReportService {

	@Autowired
	private IReportRepository reportRepository;
	
	@Override
	public Map<String, Object> getOperationalDashboard() {
		Map<String, Object> dashboard = new HashMap<>();
		dashboard.put("activeRoutes", 12);
		dashboard.put("totalTickets", 450);
		dashboard.put("complianceAlerts", 2);
		return dashboard;
	}

	@Override
	public Report runCustomReport(String scope) {

		Report report = Report.builder().scope(scope)
				.metrics("{\"status\": \"IN_PROGRESS\", \"info\":\"Analyzing " + scope + " data...\"}")
				.generatedDate(LocalDateTime.now()).build();
		return reportRepository.save(report);
	}

	@Override
	public Report getReportByJobId(Long jobId) {
		return reportRepository.findById(jobId)
				.orElseThrow(() -> new RuntimeException("Report Job ID " + jobId + " not found in the system."));

	}

}
