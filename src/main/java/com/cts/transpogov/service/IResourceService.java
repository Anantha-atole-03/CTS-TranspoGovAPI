package com.cts.transpogov.service;

import java.util.List;

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
	// 29. POST /resources/{resourceId}/utilizations → Log resource usage

}
//GET /resources?programId={id} → Get resources of program
//28. POST /resources/{resourceId}/allocate → Allocate resource
//29. POST /resources/{resourceId}/utilizations → Log resource usage
