package com.cts.transpogov.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.transpogov.dtos.ApiResponse;
import com.cts.transpogov.dtos.program.ProgramCreateRequest;
import com.cts.transpogov.dtos.program.ProgramResponse;
import com.cts.transpogov.dtos.program.ProgramUpdateRequest;
import com.cts.transpogov.enums.ProgramStatus;
import com.cts.transpogov.service.ITransportProgramService;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/programs")
@RequiredArgsConstructor
@Validated
@Slf4j
public class TransportProgramController {

	private final ITransportProgramService programService;

	/*
	 * Method: GET Argument: N/A Description: It fetch all Programs by return:
	 * ResponseEntity<ApiResponse> type
	 */
	@GetMapping("/")
	public ResponseEntity<ApiResponse<List<ProgramResponse>>> getAllPrograms() {
		return ResponseEntity.ok(new ApiResponse<List<ProgramResponse>>("All Program fetched Successfuly",
				HttpStatus.OK.value(), programService.getAllPrograms()));
	}

	/*
	 * Method: GET Argument: program Id - Type- Long Description: It fetch Programs
	 * by Id return: ResponseEntity<ApiResponse> type
	 */
	@GetMapping("/{programId}")
	public ResponseEntity<ApiResponse<ProgramResponse>> getProgram(
			@NotNull(message = "Program id should be provided") @PathVariable Long programId) {
		log.info("Program With Id {} fetching", programId);
		ProgramResponse response = programService.getProgram(programId);
		return ResponseEntity
				.ok(new ApiResponse<ProgramResponse>("Program fetched Successfuly", HttpStatus.OK.value(), response));

	}

	/*
	 * Method: POST Argument:ProgramCreateRequest Description:Add New Program
	 * return: ResponseEntity<ApiResponse> type
	 */

	@PostMapping("/")
	@Validated
	public ResponseEntity<ApiResponse<String>> addProgram(
			@NotNull(message = "Program details should be provided") @RequestBody ProgramCreateRequest program) {
		log.info("Program With Id {} creating");
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<String>(programService.addProgram(program), HttpStatus.OK.value(), null));
	}
	
	/*
	 * Method: PATCH Argument:ProgramId Description:Program submitted for approval
	 * return: ResponseEntity<ApiResponse> type
	 */
	@PatchMapping("/{programId}/submit")
	public ResponseEntity<ApiResponse<String>> submitForApproval(
			@NotNull(message = "Program id should be provided") @PathVariable Long programId) {
		log.info("Program With Id {} submitted to approval", programId);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse<>(programService.submitForApproval(programId), HttpStatus.OK.value(), null));
	}

	/*
	 * Method: PATCH Argument:ProgramId Description:Program approve with given id
	 * return: ResponseEntity<ApiResponse> type
	 */
	@PatchMapping("/{programId}/approve")
	public ResponseEntity<ApiResponse<String>> approveProgram(
			@NotNull(message = "Program id should be provided") @PathVariable Long programId) {
		log.info("Program With Id {} approving", programId);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse<>(programService.approveProgram(programId), HttpStatus.OK.value(), null));
	}

	/*
	 * Method: PATCH Argument:Program Id, Status  Description:Program status changes to give status
	 * return: ResponseEntity<ApiResponse> type
	 */
	@PatchMapping("/{programId}/status/{status}")
	public ResponseEntity<ApiResponse<String>> changeProgramStatus(
			@NotNull(message = "Program id should be provided") @PathVariable Long programId,
			@NotNull(message = "Program status should be provided") @PathVariable ProgramStatus status) {
		log.info("Program With Id {} Status {} changed", programId, status);
		return ResponseEntity.status(HttpStatus.OK).body(
				new ApiResponse<>(programService.changeProgramStatus(programId, status), HttpStatus.OK.value(), null));

	}
	

	/*
	 * Method: PUT Argument:ProgramId Description:Update Program with id
	 * return: ResponseEntity<ApiResponse> type
	 */
	@PutMapping("/{programId}")
	public ResponseEntity<ApiResponse<ProgramResponse>> updateProgram(
			@NotNull(message = "Program id should be provided") @PathVariable Long programId,
			@RequestBody ProgramUpdateRequest updateRequest) {
		log.info("Program With Id {} updating", programId);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Program Updated succesfully!",
				HttpStatus.OK.value(), programService.updateProgram(programId, updateRequest)));
	}
	/*
	 * Method: DELETE Argument:ProgramId Description:DELETE Program with id
	 * return: ResponseEntity<ApiResponse> type
	 */

	@DeleteMapping("/{programId}")
	public ResponseEntity<ApiResponse<ProgramResponse>> deleteProgram(
			@NotNull(message = "Program id should be provided") @PathVariable Long programId) {
		log.info("Program With Id {} deleting", programId);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Program Deleted succesfully!",
				HttpStatus.OK.value(), programService.deleteProgram(programId)));
	}

}
