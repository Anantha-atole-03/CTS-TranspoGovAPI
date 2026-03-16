package com.cts.transpogov.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.transpogov.models.Report;
import com.cts.transpogov.service.IReportService;

@RestController
@RequestMapping("/reports")
public class ReportController {

	@Autowired
	private IReportService reportService;
	
	// Endpoint: GET /reports/operations
	@GetMapping("/operations")
	public ResponseEntity<Map<String, Object>> getDashboard() {
		return ResponseEntity.ok(reportService.getOperationalDashboard());
	}
	
	// Endpoint: POST /reports/custom/run
	@PostMapping("/custom/run")
	public ResponseEntity<Report> runReport(@RequestParam String scope) {
		return ResponseEntity.ok(reportService.runCustomReport(scope));
	}
	
	// Endpoint: GET /reports/custom/jobs/{jobId}
	@GetMapping("/custom/jobs/{jobId}")
	public ResponseEntity<Report> getJobStatus(@PathVariable Long jobId) {
		return ResponseEntity.ok(reportService.getReportByJobId(jobId));
	}
	
}
