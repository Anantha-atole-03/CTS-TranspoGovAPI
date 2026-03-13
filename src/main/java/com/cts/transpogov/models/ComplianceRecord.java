package com.cts.transpogov.models;

import java.time.LocalDate;

import com.cts.transpogov.enums.ComplianceResultStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "compliance_records")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ComplianceRecord {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "compliance_id", updatable = false, nullable = false)
  private Long complianceId;

//  private String entityId; // Route/Ticket/Program id confused
  private String type;     // Route/Ticket/Program Confused

  @Enumerated(EnumType.STRING)
  private ComplianceResultStatus result;

  private LocalDate date;

  @Column(columnDefinition = "text")
  private String notes;
}