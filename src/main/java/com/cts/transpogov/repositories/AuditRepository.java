package com.cts.transpogov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.transpogov.models.Audit;

public interface AuditRepository extends JpaRepository<Audit, Long> {

}
