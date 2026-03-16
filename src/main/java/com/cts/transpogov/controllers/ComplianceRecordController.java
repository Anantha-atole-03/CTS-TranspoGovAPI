package com.cts.transpogov.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.transpogov.dtos.compliance.ComplianceCreateRequest;
import com.cts.transpogov.dtos.compliance.ComplianceResponse;
import com.cts.transpogov.models.ComplianceRecord;
import com.cts.transpogov.service.ComplianceRecordService;

@RestController
@RequestMapping("/compliances")
public class ComplianceRecordController {

	private final ComplianceRecordService service;

	public ComplianceRecordController(ComplianceRecordService service) {
		this.service = service;
	}

	@GetMapping("/")
	public List<ComplianceResponse> getAll() {
		return service.findAll();
	}

	@GetMapping("/get/{id}")
	public ComplianceResponse getById(@PathVariable Long id) {
		return  null;
	}
	
	
	@PostMapping("/save")
	public ResponseEntity<ComplianceResponse> create(@RequestBody ComplianceCreateRequest body) {
//		ComplianceRecord created = service.create(body);

		return null;
	}

	@PutMapping("/update/{id}")
	public ComplianceResponse update(@PathVariable Long id, @RequestBody ComplianceRecord body)
			 {
		return null;
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/summary")
	public Long getCount() {
		return service.getCount();
	}

	@GetMapping("/getByEntity/{entityId}")
	public List<ComplianceResponse> findByEntityId(@PathVariable("entityId") Long entityId)  {
		return service.findByEntityId(entityId);
	}

}