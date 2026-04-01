package com.cts.transpogov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.transpogov.models.Ticket;

@Repository
public interface ITicketRepository extends JpaRepository<Ticket, Long> {
}