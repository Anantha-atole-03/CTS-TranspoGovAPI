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

@Entity @Table(name = "reports")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Report {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "report_id", updatable = false, nullable = false)
  private Long reportId;

  private String scope; 
  @Column(columnDefinition = "text")
  private String metrics; 

  private LocalDateTime generatedDate;
}
