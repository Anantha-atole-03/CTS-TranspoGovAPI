package com.cts.transpogov.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.cts.transpogov.dtos.program.ProgramCreateRequest;
import com.cts.transpogov.dtos.program.ProgramResponse;
import com.cts.transpogov.dtos.program.ProgramUpdateRequest;
import com.cts.transpogov.enums.ProgramStatus;
import com.cts.transpogov.exceptions.ProgramNotFoundException;
import com.cts.transpogov.models.TransportProgram;
import com.cts.transpogov.repositories.TransportProgramRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TransportProgramServiceImpl implements ITransportProgramService {

	private final TransportProgramRepository programRepository;
	private final ModelMapper modelMapper;

	@Override
	public List<ProgramResponse> getAllPrograms() {

		return programRepository.findAll().stream().map(program -> modelMapper.map(program, ProgramResponse.class))
				.collect(Collectors.toList());

	}

	@Override
	public ProgramResponse getProgram(Long programId) {
		TransportProgram program = programRepository.findById(programId)
				.orElseThrow(() -> new ProgramNotFoundException("Program not found!"));
		return modelMapper.map(program, ProgramResponse.class);
	}

	@Override
	public String addProgram(ProgramCreateRequest program) {
		TransportProgram program2 = modelMapper.map(program, TransportProgram.class);
		program2.setStatus(ProgramStatus.DRAFT);
		programRepository.save(program2);
		return "Program Successfuly Added!";
	}

	@Override
	public String submitForApproval(Long programId) {
		TransportProgram program = programRepository.findById(programId)
				.orElseThrow(() -> new ProgramNotFoundException("Program not found!"));
		program.setStatus(ProgramStatus.SUBMITTED);
		programRepository.save(program);
		return "Program successfully submitted for Approvel!";
	}

	@Override
	public String approveProgram(Long programId) {
		TransportProgram program = programRepository.findById(programId)
				.orElseThrow(() -> new ProgramNotFoundException("Program not found!"));
		program.setStatus(ProgramStatus.APPROVED);
		programRepository.save(program);
		return "Program successfully Approved!";
	}

	@Override
	public String changeProgramStatus(Long programId, ProgramStatus newStatus) {

		TransportProgram program = programRepository.findById(programId)
				.orElseThrow(() -> new ProgramNotFoundException("Program not found: " + programId));

		ProgramStatus current = program.getStatus();
		if (current == newStatus) {
			return "No change: program status is already " + current.name() + ".";
		}

//		validateTransition(current, newStatus, program);

		program.setStatus(newStatus);
		programRepository.save(program);

		return "Program status changed from " + current.name() + " to " + newStatus.name() + ".";
	}

	@Override
	public ProgramResponse deleteProgram(Long programId) {
		TransportProgram program = programRepository.findById(programId)
				.orElseThrow(() -> new ProgramNotFoundException("Program not found!"));
		programRepository.delete(program);
		return modelMapper.map(program, ProgramResponse.class);
	}
	
	@Override
	public ProgramResponse updateProgram(Long programId, ProgramUpdateRequest updateRequest) {
		TransportProgram program = programRepository.findById(programId)
				.orElseThrow(() -> new ProgramNotFoundException("Program not found!"));
		if (!updateRequest.getTitle().isBlank())
			program.setTitle(updateRequest.getTitle());
		if (!updateRequest.getDescription().isBlank())
			program.setDescription(updateRequest.getDescription());
		if (updateRequest.getStartDate()!=null)
			program.setStartDate(updateRequest.getStartDate());
		if (updateRequest.getEndDate()!=null)
			program.setEndDate(updateRequest.getEndDate());
		programRepository.save(program);
		return modelMapper.map(program, ProgramResponse.class);
	}

	private void validateTransition(ProgramStatus from, ProgramStatus to, TransportProgram program) {

		switch (from) {
		case DRAFT -> {
			if (!(to == ProgramStatus.APPROVED || to == ProgramStatus.CANCELLED)) {
				throw new IllegalStateException("Cannot change status from DRAFT to " + to);
			}
		}
		case APPROVED -> {
			if (!(to == ProgramStatus.IN_PROGRESS || to == ProgramStatus.CANCELLED)) {
				throw new IllegalStateException("Cannot change status from APPROVED to " + to);
			}
		}
		case IN_PROGRESS -> {
			if (!(to == ProgramStatus.COMPLETED || to == ProgramStatus.ON_HOLD)) {
				throw new IllegalStateException("Cannot change status from IN PROGRESS to " + to);
			}
		}
		case ON_HOLD -> {
			if (!(to == ProgramStatus.IN_PROGRESS || to == ProgramStatus.CANCELLED)) {
				throw new IllegalStateException("Cannot change status from ON HOLD to " + to);
			}
		}
		case COMPLETED, CANCELLED -> 
			throw new IllegalStateException("Status " + from + " is terminal. Change to " + to + " is not allowed.");
		default -> {
		}
		}

	}

	

}
