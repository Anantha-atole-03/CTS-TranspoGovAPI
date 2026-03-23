package com.cts.transpogov.models;


import com.cts.transpogov.enums.ResourceStatus;
import com.cts.transpogov.enums.ResourceType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "resources")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Resource {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "resource_id", updatable = false, nullable = false)
  private Long resourceId;

  @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "program_id")
  @NotNull(message = "Program details required")
  private TransportProgram program; 

  @NotNull(message = "Resource type required")
  @Enumerated(EnumType.STRING)
  private ResourceType type; 
  @Positive(message = "Resource quantity shold be positive")
  @NotNull(message = "Resource type required")
  private int quantity;

  @NotNull(message = "Resource status required")
  @Enumerated(EnumType.STRING)
  private ResourceStatus status;
}