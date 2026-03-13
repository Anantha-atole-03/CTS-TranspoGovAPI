package com.cts.transpogov.dtos.ticket;

import lombok.Data;

@Data
public class PaymentCreateRequest {
  private Long ticketId;
  private String method; // Cash/Card/Wallet
}
