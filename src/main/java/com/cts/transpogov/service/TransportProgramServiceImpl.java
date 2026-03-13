package com.cts.transpogov.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.cts.transpogov.dtos.program.ProgramCreateRequest;
import com.cts.transpogov.dtos.program.ProgramResponse;
import com.cts.transpogov.enums.ProgramStatus;
import com.cts.transpogov.repositories.TransportProgramRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TransportProgramServiceImpl implements ITransportProgramService {

	private final TransportProgramRepository programRepository;

	@Override
	public List<ProgramResponse> getAllPrograms() {
		
		    return programRepository.findAll()
		        .stream()
		        .map(program -> new ProgramResponse(
		                program.getProgramId(),
		                program.getTitle(),
		                program.getDescription(),
		                program.getStartDate(),
		                program.getEndDate(),
		                program.getBudget(),
		                program.getStatus()
		        ))
		        .collect(Collectors.toList());
	
		
	}

	@Override
	public ProgramResponse getProgram(Long programId) {

		return null;
	}

	@Override
	public String addProgram(ProgramCreateRequest program) {

		return null;
	}

	@Override
	public String submitForApproval(Long programId) {

		return null;
	}

	@Override
	public String approveProgram(Long programId) {

		return null;
	}

	@Override
	public String changeProgramStatus(Long programId, ProgramStatus status) {

		return null;
	}

}
