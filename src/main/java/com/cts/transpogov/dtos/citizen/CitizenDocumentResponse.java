package com.cts.transpogov.dtos.citizen;

import java.time.LocalDate;

import com.cts.transpogov.enums.DocumentVerificationStatus;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
public class CitizenDocumentResponse {
    private Long documentId;
    private Long citizenId;
    private String docType;
    private String fileURI;
    private LocalDate uploadedDate;
    private DocumentVerificationStatus verificationStatus;
}