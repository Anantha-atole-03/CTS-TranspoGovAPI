package com.cts.transpogov.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.cts.transpogov.dtos.compliance.ComplianceCreateRequest;
import com.cts.transpogov.dtos.compliance.ComplianceResponse;
import com.cts.transpogov.enums.ComplianceResultStatus;
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

	/**
	 * Retrieves all compliance records.
	 *
	 * @return list of {@link ComplianceResponse} representing all compliance
	 *         records
	 */

	@Override
	public List<ComplianceResponse> findAll() {
		log.info("Fetching all compliance records");

		List<ComplianceResponse> result = repository.findAll().stream()
				.map(cr -> modelMapper.map(cr, ComplianceResponse.class)).toList();

		log.info("Fetched {} compliance records", result.size());
		return result;
	}

	/**
	 * Fetches a compliance record by its unique identifier.
	 *
	 * @param id the compliance record ID
	 * @return {@link ComplianceResponse} containing compliance details
	 * @throws ComplianceNotFoundException if the record does not exist
	 */

	@Override
	public ComplianceResponse findById(Long id) {
		log.info("Fetching compliance record by id: {}", id);
		ComplianceRecord complianceRecord = repository.findById(id).orElseThrow(() -> {
			log.warn("Compliance record not found for id: {}", id);
			throw new ComplianceNotFoundException("Compliance Record not found");
		});
		log.info("Fetched compliance record id: {}", complianceRecord.getComplianceId());
		return modelMapper.map(complianceRecord, ComplianceResponse.class);
	}

	/**
	 * Creates a new compliance record.
	 *
	 * @param complianceCreateRequest request object containing compliance details
	 * @return confirmation message after successful creation
	 */

	@Override
	public String create(ComplianceCreateRequest complianceCreateRequest) {
		log.info("Creating compliance record with type: {}", complianceCreateRequest.getType());
		ComplianceRecord saved = repository.save(modelMapper.map(complianceCreateRequest, ComplianceRecord.class));
		log.info("Compliance record created with id: {}", saved.getComplianceId());
		return "Record Saved successsfully";
	}

	/**
	 * Updates an existing compliance record.
	 *
	 * @param id                      the compliance record ID
	 * @param complianceCreateRequest request object containing updated values
	 * @return {@link ComplianceResponse} of the updated compliance record
	 * @throws ComplianceNotFoundException if the record does not exist
	 */

	@Override
	public ComplianceResponse update(Long id, ComplianceCreateRequest complianceCreateRequest) {
		log.info("Updating compliance record id: {}", id);
		ComplianceRecord existing = repository.findById(id).orElseThrow(() -> {
			log.warn("Compliance record not found for update, id: {}", id);
			return new ComplianceNotFoundException("Compliance Record not found");
		});

		existing.setType(complianceCreateRequest.getType());
		existing.setResult(complianceCreateRequest.getResult());
		existing.setNotes(complianceCreateRequest.getNotes());

		repository.save(existing);
		log.info("Compliance record updated, id: {}", existing.getComplianceId());
		return modelMapper.map(existing, ComplianceResponse.class);
	}

	/**
	 * Deletes a compliance record by its ID.
	 *
	 * @param id the compliance record ID
	 * @return confirmation message after successful deletion
	 * @throws ComplianceNotFoundException if the record does not exist
	 */

	@Override
	public String delete(Long id) {
		log.info("Deleting compliance record id: {}", id);
		ComplianceRecord cp = repository.findById(id).orElseThrow(() -> {
			log.warn("Compliance record not found for deletion, id: {}", id);
			return new ComplianceNotFoundException("Compliance Record not found");
		});
		repository.deleteById(cp.getComplianceId());
		log.info("Compliance record deleted, id: {}", cp.getComplianceId());
		return "Record deleted Succesfully";
	}

	/**
	 * Retrieves compliance records by associated entity ID.
	 *
	 * @param entityId the entity ID
	 * @return list of {@link ComplianceResponse} for the given entity
	 */

	@Override
	public List<ComplianceResponse> findByEntityId(Long entityId) {
		log.info("Fetching compliance records by entityId: {}", entityId);
		List<ComplianceRecord> records = repository.findByEntityId(entityId);

		List<ComplianceResponse> result = records.stream().map(cr -> modelMapper.map(cr, ComplianceResponse.class))
				.toList();
		log.info("Fetched {} compliance records for entityId: {}", result.size(), entityId);
		return result;
	}

	/**
	 * Retrieves the total count of compliance records.
	 *
	 * @return total number of compliance records
	 */

	@Override
	public Long getCount() {
		Long count = repository.count();
		log.info("Compliance records count: {}", count);
		return count;
	}

	/**
	 * Retrieves the number of compliance alerts where result status is FAIL.
	 *
	 * @return count of failed compliance records
	 */

	@Override
	public int getComplianceAlerts() {
		return repository.countByResult(ComplianceResultStatus.FAIL);
	}

}
