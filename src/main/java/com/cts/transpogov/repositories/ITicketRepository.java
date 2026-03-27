package com.cts.transpogov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cts.transpogov.models.Ticket;

public interface ITicketRepository extends JpaRepository<Ticket, Long> {
}