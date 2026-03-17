package com.cts.transpogov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.transpogov.models.AuditFinding;


public interface AuditFindingRepository extends JpaRepository<AuditFinding, Long> {

}
