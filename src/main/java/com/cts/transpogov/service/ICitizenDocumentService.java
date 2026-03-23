package com.cts.transpogov.service;

import java.util.List;

import com.cts.transpogov.dtos.citizen.CitizenDocumentCreateRequest;
import com.cts.transpogov.dtos.citizen.CitizenDocumentResponse;
import com.cts.transpogov.dtos.citizen.CitizenDocumentVerifyRequest;

public interface ICitizenDocumentService {

	CitizenDocumentResponse uploadDocument(CitizenDocumentCreateRequest request);

	CitizenDocumentResponse verifyDocument(Long documentId, CitizenDocumentVerifyRequest request);

	CitizenDocumentResponse getDocument(Long documentId);

	List<CitizenDocumentResponse> getDocumentsByCitizen(Long citizenId);

}
