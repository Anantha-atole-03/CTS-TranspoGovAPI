package com.cts.transpogov.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.cts.transpogov.enums.TicketStatus;

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

@Entity @Table(name = "tickets")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Ticket {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ticket_id", updatable = false, nullable = false)
  private Long ticketId;

  @Column(name = "citizen_id")
  private Long citizenId;

  @Column(name = "route_id")
  private Long routeId;

  private LocalDateTime date;
  private Double fareAmount;

  @Enumerated(EnumType.STRING)
  private TicketStatus status;
}
