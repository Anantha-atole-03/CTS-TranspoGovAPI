package com.cts.transpogov.service;

import java.util.List;

import com.cts.transpogov.dtos.ticket.TicketCreateRequest;
import com.cts.transpogov.dtos.ticket.TicketResponse;

public interface ITicketService {
	List<TicketResponse> getMyAllTickets(Long citizenId);

	String checkTicket(Long ticketId);

	TicketResponse getTicket(Long ticketId);

	TicketResponse bookTicket(TicketCreateRequest ticketCreateRequest);

	String cancelTicket(Long ticketId);

	String makePayment(Long ticketId, String paymentMethod);

	long countTickets();
}
