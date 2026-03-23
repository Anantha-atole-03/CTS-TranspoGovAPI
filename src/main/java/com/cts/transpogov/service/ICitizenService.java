package com.cts.transpogov.service;

import com.cts.transpogov.dtos.citizen.CitizenCreateRequest;
import com.cts.transpogov.dtos.citizen.CitizenResponse;

public interface ICitizenService {
	CitizenResponse save(CitizenCreateRequest requestDto);
}
