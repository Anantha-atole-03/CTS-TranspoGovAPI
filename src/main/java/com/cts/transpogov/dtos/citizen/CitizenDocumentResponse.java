package com.cts.transpogov.dtos.citizen;

import java.time.LocalDate;

import com.cts.transpogov.enums.DocumentVerificationStatus;

import lombok.Data;

@Data
public class CitizenDocumentResponse {
  private Long documentId;
  private Long citizenId;
  private String docType;
  private String fileURI;
  private LocalDate uploadedDate;
  private DocumentVerificationStatus verificationStatus;
}
