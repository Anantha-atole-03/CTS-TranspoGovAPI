package com.cts.transpogov.models;

import java.time.LocalDate;

import com.cts.transpogov.enums.ComplianceResultStatus;
import com.cts.transpogov.enums.EntityType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "compliance_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplianceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL-friendly
    @Column(name = "ComplianceID", updatable = false, nullable = false)
    private Long complianceId;
    
    @Column(name = "Date", nullable = false)
    private LocalDate complianceDate;

    @Column(name = "EntityID", nullable = false)
    private Long entityId;


    @Column(name = "Notes", columnDefinition = "text")
    private String notes;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Result", nullable = false, length = 20)
    private ComplianceResultStatus result;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Type", nullable = false, length = 16)
    private EntityType type; // ROUTE / TICKET / PROGRAM

    

  

  
}