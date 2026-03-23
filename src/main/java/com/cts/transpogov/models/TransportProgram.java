package com.cts.transpogov.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.cts.transpogov.enums.ProgramStatus;

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

@Entity @Table(name = "transport_programs")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TransportProgram {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "program_id", updatable = false, nullable = false)
  private Long programId;

  private String title;
  @Column(columnDefinition = "text") private String description;

  private LocalDate startDate;
  private LocalDate endDate;

  private Double budget;

  @Enumerated(EnumType.STRING)
  private ProgramStatus status;
}
