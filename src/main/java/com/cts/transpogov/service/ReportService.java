package com.cts.transpogov.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cts.transpogov.models.Report;
import com.cts.transpogov.repositories.IReportRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ReportService implements IReportService {

	private final IReportRepository reportRepo;
	private final IRouteService routeService;
	private final ITicketService ticketService;
	private final ITransportProgramService transportProgramService;
	private final IComplianceRecordService complianceRecordService;

	/*
	 * Description: Generates the operational dashboard by aggregating key metrics
	 * from routes, tickets, compliance records, and transport programs. It collects
	 * data from multiple service layers and returns a consolidated Map containing
	 * essential operational insights.
	 */

	@Override
	public Map<String, Object> getOperationalDashboard() {
		log.info("Generating operational dashboard");

		Map<String, Object> response = new HashMap<>();
		response.put("activeRoutes", routeService.countActiveRoutes());
		response.put("totalTickets", ticketService.countTickets());
		response.put("complianceAlerts", complianceRecordService.getComplianceAlerts());
		response.put("programEfficiency", transportProgramService.calculateEfficiency());
		return response;
	}

	/*
	 * Description: Executes a custom report based on the provided scope. It gathers
	 * operational metrics across routes, tickets, compliance alerts, and program
	 * efficiency, converts them into JSON format, and stores the report in the
	 * database before returning the saved Report entity.
	 */

	@Override
	public Report runCustomReport(String scope) {
		log.info("Running custom report for scope={}", scope);

		Map<String, Object> metrics = new HashMap<>();
		metrics.put("activeRoutes", routeService.countActiveRoutes());
		metrics.put("totalTickets", ticketService.countTickets());
		metrics.put("complianceAlerts", complianceRecordService.getComplianceAlerts());
		metrics.put("programEfficiency", transportProgramService.calculateEfficiency());

		String jsonMetrics = new ObjectMapper().writeValueAsString(metrics);

		Report report = Report.builder().scope(scope).metrics(jsonMetrics).build();
		return reportRepo.save(report);
	}

	/*
	 * Description: Retrieves a stored report using its jobId. It fetches the report
	 * record from the database and returns the Report entity. If the report does
	 * not exist, it throws an exception indicating that the report was not found.
	 */

	@Override
	public Report getReportByJobId(Long jobId) {
		log.info("Fetching report jobId={}", jobId);
		return reportRepo.findById(jobId).orElseThrow(() -> new RuntimeException("Report not found"));

	}

}
