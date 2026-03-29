package com.cts.transpogov.dtos.citizen;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CitizenDocumentCreateRequest {
    private Long citizenId;
    private String docType;
}