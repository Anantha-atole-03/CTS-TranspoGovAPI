package com.cts.transpogov.dtos.ticket;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.cts.transpogov.dtos.route.RouteResponse;
import com.cts.transpogov.enums.TicketStatus;

import lombok.Data;

@Data
public class TicketResponse {
  private Long ticketId;
  private Long citizenId;
  private RouteResponse route;
  private LocalDateTime date;
  private Double fareAmount;
  private TicketStatus status;
  private LocalDateTime createdAt;
}
