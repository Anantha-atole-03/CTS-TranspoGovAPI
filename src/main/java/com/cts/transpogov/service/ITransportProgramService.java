package com.cts.transpogov.service;

import java.util.List;

import com.cts.transpogov.dtos.program.ProgramCreateRequest;
import com.cts.transpogov.dtos.program.ProgramResponse;
import com.cts.transpogov.enums.ProgramStatus;


public interface ITransportProgramService {
	List<ProgramResponse> getAllPrograms();
	ProgramResponse getProgram(Long programId);
	String addProgram(ProgramCreateRequest program);
	String submitForApproval(Long programId);
	String approveProgram( Long programId);
	String changeProgramStatus(Long programId, ProgramStatus status);
	
}
