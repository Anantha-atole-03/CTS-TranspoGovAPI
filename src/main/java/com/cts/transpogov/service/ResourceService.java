package com.cts.transpogov.service;

import java.util.List;
import java.util.stream.Collectors;

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

@Service
@RequiredArgsConstructor
public class ResourceService implements IResourceService {
	private final ResourceRepository resourceRepository;
	private final TransportProgramRepository programRepository ;
	private final ModelMapper modelMapper;

	@Override
	public ResourceResponse getResource(Long resourceId) {
		Resource resource = resourceRepository.findById(resourceId).orElseThrow(()->new ResourceNotFoundException("Resource not found!"));
		ResourceResponse response=modelMapper.map(resource, ResourceResponse.class);
		response.setProgramId(resource.getProgram().getProgramId());
		return response;
	}
	
	@Override
	public List<ResourceResponse> getAllResouces() {
		return resourceRepository.findAll().stream().map(resource -> {
			ResourceResponse response = modelMapper.map(resource, ResourceResponse.class);
			response.setProgramId(resource.getProgram().getProgramId());
			return response;

		}).collect(Collectors.toList());
	}

	@Override
	public List<ResourceResponse> getAllResoucesByProgramId(Long programId) {
		return resourceRepository.findByProgramProgramId(programId).stream().map(resource -> {
			ResourceResponse response = modelMapper.map(resource, ResourceResponse.class);
			response.setProgramId(resource.getProgram().getProgramId());
			return response;

		}).collect(Collectors.toList());

	}

	@Override
	public String allocateResouce(Long resourceId) {
		Resource resource = resourceRepository.findById(resourceId)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found respective id"));
		if (resource.getStatus().equals(ResourceStatus.IN_PROCUREMENT) ) {
			resource.setStatus(ResourceStatus.ASSIGNED);
			resourceRepository.save(resource);
			return "Resource Allocated successfully";
		}else {
			throw new ResourceAllocationException("Resource already allocated or in use");
		}

	}

	@Override
	public String addResouce(ResourceCreateRequest createRequest) {
		Resource resource=modelMapper.map(createRequest,Resource.class);
		resource.setStatus(ResourceStatus.IN_PROCUREMENT);
		resource.setProgram(programRepository.findById(createRequest.getProgramId()).orElseThrow(()->new ProgramNotFoundException("Program Not found")));
		resourceRepository.save(resource);
		return "Resource added succesfully!";
	}

	@Override
	public String changeResourcStatus(Long resourceId, ResourceStatus resourceStatus) {
		Resource resource = resourceRepository.findById(resourceId).orElseThrow(()->new ResourceNotFoundException("Resource not found!"));
		resource.setStatus(resourceStatus);
		resourceRepository.save(resource);
		return "Resource status updated successfully";
	}

	@Override
	public String deleteResouce(Long resourceId) {
		Resource resource = resourceRepository.findById(resourceId).orElseThrow(()->new ResourceNotFoundException("Resource not found!"));
		resourceRepository.delete(resource);
		return "Resource deleted successfullty";
	}

	
}
