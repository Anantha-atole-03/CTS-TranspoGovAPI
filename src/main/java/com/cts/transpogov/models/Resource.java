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
  private TransportProgram program; 

  @Enumerated(EnumType.STRING)
  private ResourceType type; 
  private Double quantity;

  @Enumerated(EnumType.STRING)
  private ResourceStatus status;
}