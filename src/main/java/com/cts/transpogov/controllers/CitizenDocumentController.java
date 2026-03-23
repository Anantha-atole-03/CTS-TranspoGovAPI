package com.cts.transpogov.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.transpogov.dtos.citizen.CitizenDocumentCreateRequest;
import com.cts.transpogov.dtos.citizen.CitizenDocumentResponse;
import com.cts.transpogov.dtos.citizen.CitizenDocumentVerifyRequest;
import com.cts.transpogov.service.ICitizenDocumentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class CitizenDocumentController {

    private final ICitizenDocumentService citizenDocumentService;

    @PostMapping
    public CitizenDocumentResponse uploadDocument(@RequestBody CitizenDocumentCreateRequest request) {
        return citizenDocumentService.uploadDocument(request);
    }

    @PutMapping("/{documentId}/verify")
    public CitizenDocumentResponse verifyDocument(
            @PathVariable Long documentId,
            @RequestBody CitizenDocumentVerifyRequest request) {
        return citizenDocumentService.verifyDocument(documentId, request);
    }
    			
    @GetMapping("/{documentId}")
    public CitizenDocumentResponse getDocument(@PathVariable Long documentId) {
        return citizenDocumentService.getDocument(documentId);
    }

    @GetMapping("/citizen/{citizenId}")
    public List<CitizenDocumentResponse> getDocumentsByCitizen(@PathVariable Long citizenId) {
        return citizenDocumentService.getDocumentsByCitizen(citizenId);
    }
}