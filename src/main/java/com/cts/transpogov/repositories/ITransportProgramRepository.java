package com.cts.transpogov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cts.transpogov.models.TransportProgram;

public interface ITransportProgramRepository extends JpaRepository<TransportProgram, Long> {
}