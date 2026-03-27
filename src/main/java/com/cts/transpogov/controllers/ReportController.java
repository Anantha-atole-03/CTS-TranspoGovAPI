package com.cts.transpogov.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.transpogov.dtos.ApiResponse;
import com.cts.transpogov.dtos.report.ReportRequest;
import com.cts.transpogov.dtos.report.ReportResponse;
import com.cts.transpogov.enums.ReportScope;
import com.cts.transpogov.enums.ReportStatus;
import com.cts.transpogov.models.Report;
import com.cts.transpogov.service.IReportService;

import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/reports")
@Slf4j
@RequiredArgsConstructor
public class ReportController {

	@Autowired
	private final IReportService reportService;

	// Endpoint: GET /reports/operations

	/*
	 * Method: GET Argument: None Description: Fetches the operational dashboard
	 * data by calling getOperationalDashboard() service method Return:
	 * ResponseEntity<ApiResponse<Map<String, Object>>> type
	 */

	@GetMapping("/operations")
	public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboard() {
		log.info("Fetching operational dashboard");

		Map<String, Object> dashboard = reportService.getOperationalDashboard();

		return ResponseEntity.ok(new ApiResponse<>("Dashboard fetched", HttpStatus.OK.value(), dashboard));
	}

	// Endpoint: POST /reports/custom/run

	/*
	 * Method: POST Argument: ReportRequest (Request Body) Description: Starts
	 * execution of a custom report based on provided scope by calling
	 * runCustomReport() service method Return:
	 * ResponseEntity<ApiResponse<ReportResponse>> type
	 */

	@PostMapping("/custom/run")
	public ResponseEntity<ApiResponse<ReportResponse>> runReport(@RequestBody ReportRequest request) {
		log.info("Running report for scope={}", request.getScope());

		Report report = reportService.runCustomReport(request.getScope().name());

		ReportResponse dto = ReportResponse.builder().reportId(report.getReportId()).scope(request.getScope())
				.metrics(report.getMetrics()).status(ReportStatus.IN_PROGRESS).generatedDate(report.getGeneratedDate())
				.build();

		return ResponseEntity.ok(new ApiResponse<>("Report started", 200, dto));
	}

	// Endpoint: GET /reports/custom/jobs/{jobId}

	/*
	 * Method: GET Argument: jobId (Long) Description: Fetches status and details of
	 * a running/completed report using getReportByJobId() service method Return:
	 * ResponseEntity<ApiResponse<ReportResponse>> type
	 */

	@GetMapping("/custom/jobs/{jobId}")
	public ResponseEntity<ApiResponse<ReportResponse>> getJobStatus(@PathVariable Long jobId) {
		log.info("Fetching job status for jobId={}", jobId);

		Report report = reportService.getReportByJobId(jobId);

		ReportResponse dto = ReportResponse.builder().reportId(report.getReportId())
				.scope(ReportScope.valueOf(report.getScope())).metrics(report.getMetrics())
				.status(ReportStatus.COMPLETED).generatedDate(report.getGeneratedDate()).build();

		return ResponseEntity.ok(new ApiResponse<>("Report status fetched", 200, dto));
	}

}
