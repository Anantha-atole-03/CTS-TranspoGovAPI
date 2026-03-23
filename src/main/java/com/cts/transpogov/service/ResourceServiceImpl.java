package com.cts.transpogov.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.cts.transpogov.dtos.program.ResourceCreateRequest;
import com.cts.transpogov.dtos.program.ResourceResponse;
import com.cts.transpogov.enums.ResourceStatus;
import com.cts.transpogov.exceptions.ProgramNotFoundException;
import com.cts.transpogov.exceptions.ResourceAllocationException;
import com.cts.transpogov.exceptions.ResourceNotFoundException;
import com.cts.transpogov.models.Resource;
import com.cts.transpogov.repositories.ResourceRepository;
import com.cts.transpogov.repositories.TransportProgramRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service layer implementation for Transport Program operations.
 * 
 * @Service → Marks this class as a Spring service component. @Transactional→
 *          Ensures all DB operations are executed within a transaction.
 * @RequiredArgsConstructor → Generates constructor for final fields (Dependency
 *                          Injection).
 * @Slf4j → Enables logging using SLF4J.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceServiceImpl implements IResourceService {
	private final ResourceRepository resourceRepository;
	private final TransportProgramRepository programRepository;
	// ModelMapper for converting Entity ↔ DTO
	private final ModelMapper modelMapper;

	/**
	 * Fetch a single resource by its ID. Throws ResourceNotFoundException if
	 * resource does not exist.
	 */
	@Override
	public ResourceResponse getResource(Long resourceId) {
		Resource resource = resourceRepository.findById(resourceId)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found!"));
		ResourceResponse response = modelMapper.map(resource, ResourceResponse.class);
		// Explicitly setting programId (nested object mapping)
		response.setProgramId(resource.getProgram().getProgramId());
		return response;
	}

	/**
	 * Fetch all resources. Converts entity list into response DTO list.
	 */
	@Override
	public List<ResourceResponse> getAllResouces() {
		log.info("All Resource Fetched!");
		return resourceRepository.findAll().stream().map(resource -> {
			ResourceResponse response = modelMapper.map(resource, ResourceResponse.class);
			response.setProgramId(resource.getProgram().getProgramId());
			return response;
		}).toList();

	}

	/**
	 * Fetch all resources associated with a specific program ID.
	 */
	@Override
	public List<ResourceResponse> getAllResoucesByProgramId(Long programId) {
		log.info("Id: {} Resource Fetched!", programId);
		return resourceRepository.findByProgramProgramId(programId).stream().map(resource -> {
			ResourceResponse response = modelMapper.map(resource, ResourceResponse.class);
			response.setProgramId(resource.getProgram().getProgramId());
			return response;
		}).toList();
	}

	/**
	 * Allocates a resource. Allowed only if resource status is IN_PROCUREMENT.
	 */
	@Override
	public String allocateResouce(Long resourceId) {
		Resource resource = resourceRepository.findById(resourceId)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found respective id"));
		// Business rule: allocation allowed only in IN_PROCUREMENT state
		if (resource.getStatus().equals(ResourceStatus.IN_PROCUREMENT)) {
			resource.setStatus(ResourceStatus.ASSIGNED);
			resourceRepository.save(resource);
			log.info("Id: {} Resource allocated!", resource.getResourceId());
			return "Resource Allocated successfully";
		} else {
			throw new ResourceAllocationException("Resource already allocated or in use");
		}

	}

	/**
	 * Adds a new resource. Default status is set to IN_PROCUREMENT.
	 */
	@Override
	public String addResouce(ResourceCreateRequest createRequest) {
		Resource resource = modelMapper.map(createRequest, Resource.class);
		// Set default status on creation
		resource.setStatus(ResourceStatus.IN_PROCUREMENT);
		resource.setProgram(programRepository.findById(createRequest.getProgramId())
				.orElseThrow(() -> new ProgramNotFoundException("Program Not found")));

		resourceRepository.save(resource);
		log.info("Id: {} Resource saved!", resource.getResourceId());
		return "Resource added succesfully!";
	}

	/**
	 * Change resource status. No transition validation is applied here.
	 */
	@Override
	public String changeResourcStatus(Long resourceId, ResourceStatus resourceStatus) {
		Resource resource = resourceRepository.findById(resourceId)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found!"));
		resource.setStatus(resourceStatus);
		resourceRepository.save(resource);
		log.info("Id: {} Resource status changed to {}!", resource.getResourceId(), resource);
		return "Resource status updated successfully";
	}

	/**
	 * Deletes a resource by ID.
	 */
	@Override
	public String deleteResouce(Long resourceId) {
		Resource resource = resourceRepository.findById(resourceId)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found!"));
		log.info("Id: {} Resource deleted!", resource.getResourceId());
		resourceRepository.delete(resource);
		return "Resource deleted successfullty";
	}

}
