package com.cts.transpogov.dtos.ticket;

import java.time.LocalDateTime;

import com.cts.transpogov.enums.PaymentStatus;

import lombok.Data;

@Data
public class PaymentResponse {
  private Long paymentId;
  private Long ticketId;
  private String method;
  private LocalDateTime date;
  private PaymentStatus status;
}
