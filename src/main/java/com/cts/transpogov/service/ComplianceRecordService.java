package com.cts.transpogov.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.cts.transpogov.dtos.compliance.ComplianceCreateRequest;
import com.cts.transpogov.dtos.compliance.ComplianceResponse;
import com.cts.transpogov.exceptions.ComplianceNotFoundException;
import com.cts.transpogov.models.ComplianceRecord;
import com.cts.transpogov.repositories.ComplianceRecordRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComplianceRecordService implements IComplianceRecordService {

    private final ComplianceRecordRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public List<ComplianceResponse> findAll() {
        log.info("Fetching all compliance records");
        List<ComplianceResponse> result = repository.findAll().stream()
                .map(record -> modelMapper.map(record, ComplianceResponse.class))
                .collect(Collectors.toList());
        log.info("Fetched {} compliance records", result.size());
        return result;
    }

    @Override
    public ComplianceResponse findById(Long id) {
        log.info("Fetching compliance record by id: {}", id);
        ComplianceRecord complianceRecord = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Compliance record not found for id: {}", id);
                    return new ComplianceNotFoundException("Compliance Record not found");
                });
        log.info("Fetched compliance record id: {}", complianceRecord.getComplianceId());
        return modelMapper.map(complianceRecord, ComplianceResponse.class);
    }

    @Override
    public String create(ComplianceCreateRequest record) {
        log.info("Creating compliance record with type: {}", record.getType());
        ComplianceRecord saved = repository.save(modelMapper.map(record, ComplianceRecord.class));
        log.info("Compliance record created with id: {}", saved.getComplianceId());
        return "Record Saved successsfully";
    }

    @Override
    public ComplianceResponse update(Long id, ComplianceCreateRequest record) {
        log.info("Updating compliance record id: {}", id);
        ComplianceRecord existing = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Compliance record not found for update, id: {}", id);
                    return new ComplianceNotFoundException("Compliance Record not found");
                });

        existing.setType(record.getType());
        existing.setResult(record.getResult());
        existing.setNotes(record.getNotes());

        repository.save(existing);
        log.info("Compliance record updated, id: {}", existing.getComplianceId());
        return modelMapper.map(existing, ComplianceResponse.class);
    }

    @Override
    public String delete(Long id) {
        log.info("Deleting compliance record id: {}", id);
        ComplianceRecord cp = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Compliance record not found for deletion, id: {}", id);
                    return new ComplianceNotFoundException("Compliance Record not found");
                });
        repository.deleteById(cp.getComplianceId());
        log.info("Compliance record deleted, id: {}", cp.getComplianceId());
        return "Record deleted Succesfully";
    }

    @Override
    public List<ComplianceResponse> findByEntityId(Long entityId) {
        log.info("Fetching compliance records by entityId: {}", entityId);
        List<ComplianceRecord> records = repository.findByEntityId(entityId);
        List<ComplianceResponse> result = records.stream()
                .map(record -> modelMapper.map(record, ComplianceResponse.class))
                .collect(Collectors.toList());
        log.info("Fetched {} compliance records for entityId: {}", result.size(), entityId);
        return result;
    }

    @Override
    public Long getCount() {
        Long count = repository.count();
        log.info("Compliance records count: {}", count);
        return count;
    }
}