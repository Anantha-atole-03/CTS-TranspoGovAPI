package com.cts.transpogov.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class TransportProgramService implements ITransportProgramService {

	@Override
	public double calculateEfficiency() {
		 return 87.5; // placeholder logic
	}

}
