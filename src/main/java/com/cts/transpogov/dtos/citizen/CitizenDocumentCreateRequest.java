package com.cts.transpogov.dtos.citizen;


import lombok.Data;

@Data
public class CitizenDocumentCreateRequest {
    private Long citizenId;
    private String docType;
}