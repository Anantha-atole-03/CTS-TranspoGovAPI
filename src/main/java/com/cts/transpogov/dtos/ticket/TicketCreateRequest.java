package com.cts.transpogov.dtos.ticket;


import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TicketCreateRequest {
  private Long citizenId;
  private Long routeId;
  private LocalDateTime date;
  private Double fareAmount;
//  private TicketStatus status; 
}
