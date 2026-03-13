package com.cts.transpogov.dtos.citizen;


import com.cts.transpogov.enums.DocumentVerificationStatus;

import lombok.Data;

@Data
public class CitizenDocumentVerifyRequest {
  private DocumentVerificationStatus verificationStatus;
  private String notes;
}
