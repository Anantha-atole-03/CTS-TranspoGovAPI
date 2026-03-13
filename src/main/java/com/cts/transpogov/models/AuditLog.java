package com.cts.transpogov.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "audit_logs")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AuditLog {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "audit_id", updatable = false, nullable = false)
  private Long auditId;

  @Column(nullable = false)
  private Long userId; 

  private String action;
  private String resource;

  @Column(nullable = false)
  private LocalDateTime timestamp;
}