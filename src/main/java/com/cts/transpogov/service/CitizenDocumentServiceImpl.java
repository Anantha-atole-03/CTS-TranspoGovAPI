package com.cts.transpogov.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public CitizenDocumentResponse uploadDocument(
            CitizenDocumentCreateRequest request,
            String filePath,
            String verificationStatus
    ) {

        log.info("Uploading document for citizenId={}", request.getCitizenId());

        Citizen citizen = citizenRepository.findById(request.getCitizenId())
                .orElseThrow(() -> new RuntimeException("Citizen not found"));

        CitizenDocument document = CitizenDocument.builder()
                .citizen(citizen)
                .docType(request.getDocType())
                .fileURI(filePath)
                .uploadedDate(LocalDate.now())
                .verificationStatus(
                        DocumentVerificationStatus.valueOf(verificationStatus)
                )
                .build();

        CitizenDocument saved = citizenDocumentRepository.save(document);
        return mapToResponse(saved);
    }
    @Override
    public CitizenDocumentResponse verifyDocument(
            Long documentId,
            CitizenDocumentVerifyRequest request) {

        CitizenDocument document = citizenDocumentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        document.setVerificationStatus(request.getVerificationStatus());

        CitizenDocument updated = citizenDocumentRepository.save(document);
        return mapToResponse(updated);
    }

    @Override
    public CitizenDocumentResponse getDocument(Long documentId) {
        CitizenDocument document = citizenDocumentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        return mapToResponse(document);
    }

    @Override
    public List<CitizenDocumentResponse> getDocumentsByCitizen(Long citizenId) {
        return citizenDocumentRepository.findByCitizen_CitizenId(citizenId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /* ---------------- Helper Methods ---------------- */

    private CitizenDocumentResponse mapToResponse(CitizenDocument doc) {
        CitizenDocumentResponse res = new CitizenDocumentResponse();
        res.setDocumentId(doc.getDocumentId());
        res.setCitizenId(doc.getCitizen().getCitizenId());
        res.setDocType(doc.getDocType());
        res.setFileURI(doc.getFileURI());
        res.setUploadedDate(doc.getUploadedDate());
        res.setVerificationStatus(doc.getVerificationStatus());
        return res;
    }

    private String saveFile(MultipartFile file) {
        try {
            Path uploadDir = Paths.get("uploads");
            Files.createDirectories(uploadDir);

            Path path = uploadDir.resolve(
                    UUID.randomUUID() + "_" + file.getOriginalFilename()
            );

            Files.copy(file.getInputStream(), path);

            return path.toString();
        } catch (Exception e) {
            throw new RuntimeException("File upload failed", e);
        }
    }

}