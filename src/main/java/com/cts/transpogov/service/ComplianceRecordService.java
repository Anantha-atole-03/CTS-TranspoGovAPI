package com.cts.transpogov.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.cts.transpogov.dtos.compliance.ComplianceCreateRequest;
import com.cts.transpogov.dtos.compliance.ComplianceResponse;
import com.cts.transpogov.exceptions.ComplianceNotFoundException;
import com.cts.transpogov.models.ComplianceRecord;
import com.cts.transpogov.repositories.ComplianceRecordRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComplianceRecordService implements IComplianceRecordService {

	private final ComplianceRecordRepository repository;
	private final ModelMapper modelMapper;

	@Override
	public List<ComplianceResponse> findAll() {

		return repository.findAll().stream().map(record -> modelMapper.map(record, ComplianceResponse.class))
				.collect(Collectors.toList());
	}

	@Override
	public ComplianceResponse findById(Long id) {
		ComplianceRecord complianceRecord = repository.findById(id)
				.orElseThrow(() -> new ComplianceNotFoundException("Compliance Record not found"));
		return modelMapper.map(complianceRecord, ComplianceResponse.class);

	}

	@Override
	public String create(ComplianceCreateRequest record) {

		repository.save(modelMapper.map(record, ComplianceRecord.class));
		return "Record Saved successsfully";
	}

	@Override
	public ComplianceResponse update(Long id, ComplianceCreateRequest record) {

		ComplianceRecord existing = repository.findById(id)
				.orElseThrow(() -> new ComplianceNotFoundException("Compliance Record not found"));
		existing.setType(record.getType());
		existing.setResult(record.getResult());
		existing.setNotes(record.getNotes());

		repository.save(existing);
		return modelMapper.map(existing, ComplianceResponse.class);
	}

	@Override
	public String delete(Long id) {
		ComplianceRecord cp = repository.findById(id)
				.orElseThrow(() -> new ComplianceNotFoundException("Compliance Record not found"));
		repository.deleteById(cp.getComplianceId());
		return "Record deleted Succesfully";
	}

	@Override
	public List<ComplianceResponse> findByEntityId(Long entityId) {
		List<ComplianceRecord> op = repository.findByEntityId(entityId);
		return op.stream().map(record -> modelMapper.map(record, ComplianceResponse.class))
				.collect(Collectors.toList());

	}

	@Override
	public Long getCount() {
		return repository.count();
	}
}
