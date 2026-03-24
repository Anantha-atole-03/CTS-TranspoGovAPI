package com.cts.transpogov.service;

import org.springframework.stereotype.Service;

import com.cts.transpogov.repositories.ITicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService {

    private final ITicketRepository ticketRepo;

    @Override
    public long countTickets() {
        return ticketRepo.count();
    }


}
