package com.cts.transpogov.service;

import java.util.List;
import com.cts.transpogov.dtos.citizen.CitizenCreateRequest;
import com.cts.transpogov.dtos.citizen.CitizenResponse;
import com.cts.transpogov.dtos.citizen.CitizenUpdateRequest;

public interface ICitizenService {
    CitizenResponse save(CitizenCreateRequest requestDto);
    CitizenResponse addCitizen(CitizenCreateRequest request);
    
    CitizenResponse updateCitizen(Long id, CitizenUpdateRequest request);
    
    CitizenResponse getCitizenById(Long id);
    
    List<CitizenResponse> getAll();
}
