package com.cts.transpogov.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cts.transpogov.dtos.citizen.CitizenDocumentCreateRequest;
import com.cts.transpogov.dtos.citizen.CitizenDocumentResponse;
import com.cts.transpogov.dtos.citizen.CitizenDocumentVerifyRequest;
public interface ICitizenDocumentService {

//    CitizenDocumentResponse uploadDocument(
//            CitizenDocumentCreateRequest request,
//            MultipartFile file
//    );

    CitizenDocumentResponse verifyDocument(
            Long documentId,
            CitizenDocumentVerifyRequest request
    );

    CitizenDocumentResponse getDocument(Long documentId);

    List<CitizenDocumentResponse> getDocumentsByCitizen(Long citizenId);

	CitizenDocumentResponse uploadDocument(CitizenDocumentCreateRequest request, String filePath,
			String verificationStatus);
}