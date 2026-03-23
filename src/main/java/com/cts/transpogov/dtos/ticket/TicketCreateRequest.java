package com.cts.transpogov.dtos.ticket;



import com.cts.transpogov.enums.TicketStatus;

import lombok.Data;

@Data
public class TicketCreateRequest {
  private Long citizenId;
  private Long routeId;
  private Long date;
  private Double fareAmount;
  private TicketStatus status; 
}
