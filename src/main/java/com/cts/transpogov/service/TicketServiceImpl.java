package com.cts.transpogov.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.cts.transpogov.dtos.route.RouteResponse;
import com.cts.transpogov.dtos.ticket.TicketCreateRequest;
import com.cts.transpogov.dtos.ticket.TicketResponse;
import com.cts.transpogov.enums.PaymentMethod;
import com.cts.transpogov.enums.PaymentStatus;
import com.cts.transpogov.enums.TicketStatus;
import com.cts.transpogov.exceptions.CitizenNotFoundException;
import com.cts.transpogov.exceptions.TicketNotFoundException;
import com.cts.transpogov.exceptions.TicketStatusException;
import com.cts.transpogov.models.Citizen;
import com.cts.transpogov.models.Payment;
import com.cts.transpogov.models.Ticket;
import com.cts.transpogov.repositories.CitizenRepository;
import com.cts.transpogov.repositories.PaymentRepository;
import com.cts.transpogov.repositories.RouteRepository;
import com.cts.transpogov.repositories.TicketRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for Ticket-related operations.
 *
 * @Service → Marks this as a Spring service component
 * @Slf4j → Enables SLF4J logging
 * @RequiredArgsConstructor → Constructor-based dependency injection
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements ITicketService {

	private final ModelMapper modelMapper;
	private final TicketRepository ticketRepository;
	private final CitizenRepository citizenRepository;
	private final RouteRepository routeRepository;
	private final PaymentRepository paymentRepository;

	/**
	 * Fetch all tickets booked by a specific citizen.
	 */
	@Override
	public List<TicketResponse> getMyAllTickets(Long citizenId) {

		log.info("Fetching all tickets for CitizenId: {}", citizenId);

		Citizen citizen = citizenRepository.findById(citizenId).orElseThrow(() -> {
			log.error("Citizen not found with id: {}", citizenId);
			return new CitizenNotFoundException("Citizen account found with user");
		});

		// Fetch tickets and map to response DTOs
		return ticketRepository.findByCitizenOrderByDateDesc(citizen).stream().map(ticket -> {

			TicketResponse response = modelMapper.map(ticket, TicketResponse.class);
			response.setCitizenId(ticket.getCitizen().getCitizenId());
			response.setRoute(modelMapper.map(ticket.getRoute(), RouteResponse.class));
			return response;
		}).collect(Collectors.toList());
	}

	/**
	 * Validates and confirms a ticket based on its current status.
	 */
	@Override
	public String checkTicket(Long ticketId) {

		log.info("Checking ticket status for TicketId: {}", ticketId);

		Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> {
			log.error("Ticket not found with id: {}", ticketId);
			return new TicketNotFoundException("Invalid ticket id");
		});

		// Validate ticket status before confirmation
		if (ticket.getStatus().equals(TicketStatus.CANCELLED)) {
			log.warn("Ticket {} is CANCELLED", ticketId);
			throw new TicketStatusException("Ticket is cancelled");
		}

		if (ticket.getStatus().equals(TicketStatus.PENDING_PAYMENT)) {
			log.warn("Ticket {} payment pending", ticketId);
			throw new TicketStatusException("Ticket payment is pending, complete payment");
		}

		if (ticket.getStatus().equals(TicketStatus.EXPIRED)) {
			log.warn("Ticket {} is EXPIRED", ticketId);
			throw new TicketStatusException("Ticket is already expired");
		}

		// Update ticket status to CONFIRMED
		ticket.setStatus(TicketStatus.CONFIRMED);
		ticketRepository.save(ticket);

		log.info("Ticket {} confirmed successfully", ticketId);

		return "Ticket Confirmed";
	}

	/**
	 * Fetch a single ticket by its ID.
	 */
	@Override
	public TicketResponse getTicket(Long ticketId) {

		log.info("Fetching ticket details for TicketId: {}", ticketId);

		Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> {
			log.error("Ticket not found with id: {}", ticketId);
			return new TicketNotFoundException("Invalid ticket");
		});
		TicketResponse response = modelMapper.map(ticket, TicketResponse.class);
		response.setCitizenId(ticket.getCitizen().getCitizenId());
		response.setRoute(modelMapper.map(ticket.getRoute(), RouteResponse.class));

		return response;
	}

	/**
	 * Books a new ticket for a citizen. Initial ticket status is set to
	 * PENDING_PAYMENT.
	 */
	@Override
	public TicketResponse bookTicket(TicketCreateRequest ticketCreateRequest) {

		log.info("Booking ticket for CitizenId: {}", ticketCreateRequest.getCitizenId());

		// Validate citizen
		Citizen citizen = citizenRepository.findById(ticketCreateRequest.getCitizenId()).orElseThrow(() -> {
			log.error("Citizen not found with id: {}", ticketCreateRequest.getCitizenId());
			return new CitizenNotFoundException("Citizen account not found");
		});

		Ticket ticket = modelMapper.map(ticketCreateRequest, Ticket.class);
		log.warn(ticket.toString());
		ticket.setCitizen(citizen);
		ticket.setStatus(TicketStatus.PENDING_PAYMENT);

		ticket.setRoute(routeRepository.findById(ticketCreateRequest.getRouteId())
				.orElseThrow(() -> new RuntimeException("Route not found")));
		Ticket savedTicket = ticketRepository.save(ticket);

		log.info("Ticket booked successfully. TicketId: {}", savedTicket.getTicketId());

		TicketResponse response = modelMapper.map(savedTicket, TicketResponse.class);
		response.setCitizenId(citizen.getCitizenId());

		return response;
	}

	@Override
	public String cancelTicket(Long ticketId) {

		log.info("Cancel ticket request received for TicketId: {}", ticketId);

		// Fetch ticket or throw exception
		Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> {
			log.error("Ticket not found with id: {}", ticketId);
			return new TicketNotFoundException("Invalid ticket id");
		});

		// Validate current ticket status
		if (ticket.getStatus().equals(TicketStatus.CANCELLED)) {
			log.warn("Ticket {} is already cancelled", ticketId);
			throw new TicketStatusException("Ticket is already cancelled");
		}

		if (ticket.getStatus().equals(TicketStatus.EXPIRED)) {
			log.warn("Ticket {} is expired and cannot be cancelled", ticketId);
			throw new TicketStatusException("Expired ticket cannot be cancelled");
		}

		// Update status to CANCELLED
		ticket.setStatus(TicketStatus.CANCELLED);
		ticketRepository.save(ticket);

		log.info("Ticket {} cancelled successfully", ticketId);

		return "Ticket cancelled successfully";
	}

	@Override
	public String makePayment(Long ticketId, String paymentMethod) {

		log.info("Initiating payment for TicketId: {}", ticketId);

		Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> {
			log.error("Ticket not found with id: {}", ticketId);
			return new TicketNotFoundException("Invalid ticket id");
		});

		if (!ticket.getStatus().equals(TicketStatus.PENDING_PAYMENT)) {
			log.warn("Payment not allowed for TicketId {} with status {}", ticketId, ticket.getStatus());
			throw new TicketStatusException("Payment not allowed for ticket status: " + ticket.getStatus());
		}

		Payment payment = Payment.builder().ticket(ticket).method(PaymentMethod.valueOf(paymentMethod.toUpperCase()))
				.date(LocalDateTime.now()).status(PaymentStatus.SUCCESS).build();

		paymentRepository.save(payment);

		ticket.setStatus(TicketStatus.CONFIRMED);
		ticketRepository.save(ticket);

		log.info("Payment successful for TicketId: {}", ticketId);

		return "Payment successful. Ticket confirmed.";
	}

	@Override
	public long countTickets() {
		return ticketRepository.count();
	}
}