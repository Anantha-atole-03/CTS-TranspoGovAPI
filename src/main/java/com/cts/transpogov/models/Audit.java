package com.cts.transpogov.models;

import java.time.LocalDate;

import com.cts.transpogov.enums.AuditStatus;

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

@Entity @Table(name = "audits")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Audit {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "audit_id", updatable = false, nullable = false)
  private Long auditId;

  private Long officerId; // reference to User, kept as Long

  private String scope;     // e.g., "Program:P-045" or "Route:R-006"
  @Column(columnDefinition = "text")
  private String findings;  // JSON or text blob if not normalized

  private LocalDate date;

  @Enumerated(EnumType.STRING)
  private AuditStatus status;
}