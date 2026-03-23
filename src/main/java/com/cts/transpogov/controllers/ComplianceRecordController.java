package com.cts.transpogov.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cts.transpogov.dtos.ApiResponse;
import com.cts.transpogov.dtos.compliance.ComplianceCreateRequest;
import com.cts.transpogov.dtos.compliance.ComplianceResponse;
import com.cts.transpogov.service.ComplianceRecordService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/compliances")
@RequiredArgsConstructor
public class ComplianceRecordController {

	private final ComplianceRecordService service;

	@GetMapping("/")
	public ResponseEntity<?> getAll() {
		List<ComplianceResponse> data = service.findAll();
		return ResponseEntity
				.ok(new ApiResponse<List<ComplianceResponse>>("Records fetched!", HttpStatus.OK.value(), data));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<ComplianceResponse>> getById(@PathVariable Long id) {
		ComplianceResponse data = service.findById(id);
		return ResponseEntity.ok(new ApiResponse<>("Record fetched!", HttpStatus.OK.value(), data));
	}

	@PostMapping("/save")
	public ResponseEntity<ApiResponse<String>> create(@RequestBody ComplianceCreateRequest body) {
		String message = service.create(body);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<>(message, HttpStatus.CREATED.value(), null));
	}



	@PutMapping("/update/{id}")
	public ResponseEntity<ApiResponse<ComplianceResponse>> update(@PathVariable Long id,
			@RequestBody ComplianceCreateRequest body) {
		ComplianceResponse updated = service.update(id, body);
		return ResponseEntity.ok(new ApiResponse<>("Record updated successfully", HttpStatus.OK.value(), updated));
	}

	// DELETE /compliances/delete/{id}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
		String message = service.delete(id);
		return ResponseEntity.ok(new ApiResponse<>(message, HttpStatus.OK.value(), null));
	}

	// GET /compliances/summary
	@GetMapping("/summary")
	public ResponseEntity<ApiResponse<Long>> getCount() {
		Long count = service.getCount();
		return ResponseEntity.ok(new ApiResponse<>("Count fetched!", HttpStatus.OK.value(), count));
	}

	// GET /compliances/getByEntity/{entityId}
	@GetMapping("/getByEntity/{entityId}")
	public ResponseEntity<ApiResponse<List<ComplianceResponse>>> findByEntityId(
			@PathVariable("entityId") Long entityId) {
		List<ComplianceResponse> list = service.findByEntityId(entityId);
		return ResponseEntity.ok(new ApiResponse<>("Records fetched by entityId!", HttpStatus.OK.value(), list));
	}

}