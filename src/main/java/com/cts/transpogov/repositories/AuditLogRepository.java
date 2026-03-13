package com.cts.transpogov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cts.transpogov.models.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

}
