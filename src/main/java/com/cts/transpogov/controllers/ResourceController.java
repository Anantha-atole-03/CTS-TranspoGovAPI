package com.cts.transpogov.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.transpogov.dtos.ApiResponse;
import com.cts.transpogov.dtos.program.ProgramUtilization;
import com.cts.transpogov.dtos.program.ResourceCreateRequest;
import com.cts.transpogov.dtos.program.ResourceResponse;
import com.cts.transpogov.enums.ResourceStatus;
import com.cts.transpogov.service.IResourceService;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ResourceController {
	private final IResourceService resourceService;
	/*
	 * Method: GET Description: It fetch all resources return:
	 * ResponseEntity<ApiResponse> type
	 */
	@GetMapping("/")
	public ResponseEntity<ApiResponse<List<ResourceResponse>>> getAllResources() {
		log.info("All resources feteched!");
		return ResponseEntity.ok(new ApiResponse<>("Resource fetched successfully", HttpStatus.OK.value(),
				resourceService.getAllResouces()));
	}

	/*
	 * Method: GET Argument: resourceId - Type- Long Description: It fetch all
	 * resource by provided id return: ResponseEntity<ApiResponse> type
	 */
	@GetMapping("/{resourceId}")
	public ResponseEntity<ApiResponse<ResourceResponse>> getResource(@PathVariable Long resourceId) {
		log.info("Resource with id {} feteched", resourceId);
		return ResponseEntity.ok(new ApiResponse<>("Resource fetched successfully", HttpStatus.OK.value(),
				resourceService.getResource(resourceId)));
	}

	/*
	 * Method: GET Argument: resourceId - Type- Long Description: It fetch all
	 * resource by provided program id return: ResponseEntity<ApiResponse> type
	 */
	@GetMapping("")
	public ResponseEntity<ApiResponse<List<ResourceResponse>>> getAllResourcesByProgram(
			@RequestParam @NotNull(message = "Program id is required") Long programId) {
		log.info("Resource with program id {} feteched", programId);
		return ResponseEntity.ok(new ApiResponse<>("Resource fetched successfully", HttpStatus.OK.value(),
				resourceService.getAllResoucesByProgramId(programId)));
	}

	/*
	 * Method: POST Argument: Resource Request Dto Description: Accepts Resource
	 * Request Dto and call add Resource method return: ResponseEntity<ApiResponse>
	 * type
	 */
	@PostMapping("/")
	public ResponseEntity<ApiResponse<String>> addResource(
			@RequestBody @NotNull(message = "Resource data required") ResourceCreateRequest createRequest) {
		log.info("new Resource added");
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<>(resourceService.addResouce(createRequest), HttpStatus.CREATED.value(), null));
	}

	/*
	 * Method: PATCH Argument: Resource id and Status Description: Accepts Resource
	 * id and status and call service method return: ResponseEntity<ApiResponse>
	 * type
	 */
	@PatchMapping("/{resourceId}")
	public ResponseEntity<ApiResponse<String>> changeResourceStatus(
			@PathVariable @NotNull(message = "Resource Id required") Long resourceId,
			@NotNull(message = "Resource Status required") @RequestParam ResourceStatus status) {
		log.info("Resource status changed id:{} status:{}", resourceId, status);
		return ResponseEntity.ok(new ApiResponse<>(resourceService.changeResourcStatus(resourceId, status),
				HttpStatus.OK.value(), null));

	}

	/*
	 * Method: PATCH Argument: Resource id Description: Accepts Resource id and call
	 * service method return: ResponseEntity<ApiResponse> type
	 */
	@PatchMapping("/{resourceId}/allocate")
	public ResponseEntity<ApiResponse<String>> allocateResource(
			@PathVariable @NotNull(message = "Resource Id required") Long resourceId) {
		log.info("Resource allocated id:{} ", resourceId);
		return ResponseEntity
				.ok(new ApiResponse<>(resourceService.allocateResouce(resourceId), HttpStatus.OK.value(), null));

	}

	/*
	 * Method: DELETE Argument: Resource id Description: Accepts Resource id and
	 * call deleteResource() service method return: ResponseEntity<ApiResponse> type
	 */
	@DeleteMapping("/{resourceId}")
	public ResponseEntity<ApiResponse<String>> deleteResource(@PathVariable Long resourceId) {
		log.info("Resource deleted with id:{} ", resourceId);
		return ResponseEntity
				.ok(new ApiResponse<>(resourceService.deleteResouce(resourceId), HttpStatus.OK.value(), null));

	}

	// 29. GET /resources/{programId}/utilizations → Log resource usage
	@GetMapping("/{programId}/utilizations")
	public ResponseEntity<ApiResponse<ProgramUtilization>> getUtilizedResourcesForProgram(
			@PathVariable Long programId) {
		log.info("All resources feteched!");
		return ResponseEntity.ok(new ApiResponse<>("Resource Log fetched successfully", HttpStatus.OK.value(),
				resourceService.getResourceUtilization(programId)));
	}

}
