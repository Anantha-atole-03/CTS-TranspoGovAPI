package com.cts.transpogov.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cts.transpogov.dtos.compliance.ComplianceCreateRequest;
import com.cts.transpogov.dtos.compliance.ComplianceResponse;
import com.cts.transpogov.exceptions.ComplianceNotFoundException;
import com.cts.transpogov.models.ComplianceRecord;
import com.cts.transpogov.repositories.ComplianceRecordRepository;

@Service
public class ComplianceRecordService implements IComplianceRecordService {

	private final ComplianceRecordRepository repository;

	public ComplianceRecordService(ComplianceRecordRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<ComplianceResponse> findAll() {
		return null;
	}

	@Override
	public ComplianceResponse findById(Long id) {
		 repository.findById(id)
				.orElseThrow(() -> new ComplianceNotFoundException("Compliance Record not found with id: " + id));
		 return null;

	}

	@Override
	public ComplianceResponse create(ComplianceCreateRequest record) {

//		 repository.save(record);
		return null;
	}

	@Override
	public ComplianceResponse update(Long id, ComplianceCreateRequest record) {

		ComplianceRecord existing = findById(id);
		existing.setType(record.getType());
		existing.setResult(record.getResult());
		existing.setNotes(record.getNotes());

		repository.save(existing);
		return null;
	}

	@Override
	public void delete(Long id) {
		ComplianceRecord cp = findById(id);
		repository.deleteById(cp.getComplianceId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplianceResponse> findByEntityId(Long entityId) {
		Optional<ComplianceRecord> op = repository.findById(entityId);
		return null;

	}

	@Override
	public Long getCount() {
		return repository.count();
	}
}
