package com.cts.transpogov.service;

import java.util.List;

import com.cts.transpogov.dtos.program.ProgramUtilization;
import com.cts.transpogov.dtos.program.ResourceCreateRequest;
import com.cts.transpogov.dtos.program.ResourceResponse;
import com.cts.transpogov.enums.ResourceStatus;

public interface IResourceService {
	ResourceResponse getResource(Long resourceId);

	List<ResourceResponse> getAllResouces();

	List<ResourceResponse> getAllResoucesByProgramId(Long programId);

	String addResouce(ResourceCreateRequest createRequest);

	String allocateResouce(Long resourceId);

	String changeResourcStatus(Long resourceId, ResourceStatus resourceStatus);

	String deleteResouce(Long resourceId);

	ProgramUtilization getResourceUtilization(Long progrmaId);

}
