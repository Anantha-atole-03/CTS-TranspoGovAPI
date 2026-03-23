package com.cts.transpogov.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.cts.transpogov.dtos.citizen.CitizenDocumentCreateRequest;
import com.cts.transpogov.dtos.citizen.CitizenDocumentResponse;
import com.cts.transpogov.dtos.citizen.CitizenDocumentVerifyRequest;
import com.cts.transpogov.enums.DocumentVerificationStatus;
import com.cts.transpogov.models.Citizen;
import com.cts.transpogov.models.CitizenDocument;
import com.cts.transpogov.repositories.CitizenDocumentRepository;
import com.cts.transpogov.repositories.CitizenRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@RequiredArgsConstructor
@Slf4j
public class CitizenDocumentServiceImpl implements ICitizenDocumentService {

    private final CitizenDocumentRepository citizenDocumentRepository;
    private final CitizenRepository citizenRepository;
    private final ModelMapper mapper;

    @Override
    public CitizenDocumentResponse uploadDocument(CitizenDocumentCreateRequest request) {
        log.info("Uploading document for citizen ID: {}", request.getCitizenId());
        
        Citizen citizen = citizenRepository.findById(request.getCitizenId())
                .orElseThrow(() -> new RuntimeException("Citizen not found")); // Better error handling

        CitizenDocument document = mapper.map(request, CitizenDocument.class);
        document.setCitizen(citizen);
        document.setVerificationStatus(DocumentVerificationStatus.PENDING);

        CitizenDocument savedDoc = citizenDocumentRepository.save(document);
        return mapper.map(savedDoc, CitizenDocumentResponse.class);
    }

    @Override
    public CitizenDocumentResponse verifyDocument(Long documentId, CitizenDocumentVerifyRequest request) {
        log.info("Verifying document ID: {}", documentId);
        
        Citizen document = citizenDocumentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        mapper.map(request, document);
        
        Citizen updatedDoc = citizenDocumentRepository.save(document);
        return mapper.map(updatedDoc, CitizenDocumentResponse.class);
    }

    @Override
    public CitizenDocumentResponse getDocument(Long documentId) {
        return citizenDocumentRepository.findById(documentId)
                .map(doc -> mapper.map(doc, CitizenDocumentResponse.class))
                .orElseThrow(() -> new RuntimeException("Document not found"));
    }

    @Override
    public List<CitizenDocumentResponse> getDocumentsByCitizen(Long citizenId) {
        return citizenDocumentRepository.findByCitizenId(citizenId)
                .stream()
                .map(doc -> mapper.map(doc, CitizenDocumentResponse.class))
                .collect(Collectors.toList());
    }
}