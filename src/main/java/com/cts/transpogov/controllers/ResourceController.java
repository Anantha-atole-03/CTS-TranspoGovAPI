package com.cts.transpogov.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.cts.transpogov.dtos.program.ResourceCreateRequest;
import com.cts.transpogov.dtos.program.ResourceResponse;
import com.cts.transpogov.enums.ResourceStatus;
import com.cts.transpogov.service.IResourceService;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
@Validated
public class ResourceController {

	private final IResourceService resourceService;
	
	
	private static final Logger log=LoggerFactory.getLogger(ResourceController.class);
	
	@GetMapping("/")
	public ResponseEntity<ApiResponse<List<ResourceResponse>>> getAllResources() {
		return ResponseEntity.ok(new ApiResponse<>("Resource fetched successfully", HttpStatus.OK.value(),
				resourceService.getAllResouces()));
	}
	@GetMapping("/{resourceId}")
	public ResponseEntity<ApiResponse<ResourceResponse>> getResource(@PathVariable Long resourceId) {
		log.info("Resource feteched");
		return ResponseEntity.ok(new ApiResponse<>("Resource fetched successfully", HttpStatus.OK.value(),
				resourceService.getResource(resourceId)));
	}

	@GetMapping("")
	public ResponseEntity<ApiResponse<List<ResourceResponse>>> getAllResourcesByProgram(@RequestParam @NotNull(message = "Program id is required") Long programId) {
		return ResponseEntity.ok(new ApiResponse<>("Resource fetched successfully", HttpStatus.OK.value(),
				resourceService.getAllResoucesByProgramId(programId)));
	}

	@PostMapping("/")
	public ResponseEntity<ApiResponse<String>> addResource(@RequestBody @NotNull(message = "Resource data required") ResourceCreateRequest createRequest) {
	
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<>(resourceService.addResouce(createRequest), HttpStatus.CREATED.value(), null));
	}

	@PatchMapping("/{resourceId}")
	public ResponseEntity<ApiResponse<String>> changeResourceStatus(@PathVariable @NotNull(message = "Resource Id required") Long resourceId,
			@NotNull(message = "Resource Status required") @RequestParam ResourceStatus status) {
		return ResponseEntity.ok(new ApiResponse<>(resourceService.changeResourcStatus(resourceId, status),
				HttpStatus.OK.value(), null));

	}
	@PatchMapping("/{resourceId}/allocate")
	public ResponseEntity<ApiResponse<String>> allocateResource(@PathVariable @NotNull(message = "Resource Id required") Long resourceId) {
		return ResponseEntity.ok(new ApiResponse<>(resourceService.allocateResouce(resourceId),
				HttpStatus.OK.value(), null));

	}
	@DeleteMapping("/{resourceId}")
	public ResponseEntity<ApiResponse<String>> deleteResource(@PathVariable Long resourceId) {
		return ResponseEntity.ok(new ApiResponse<>(resourceService.deleteResouce(resourceId),
				HttpStatus.OK.value(), null));

	}

}
