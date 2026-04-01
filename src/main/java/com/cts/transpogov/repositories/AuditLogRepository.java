package com.cts.transpogov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cts.transpogov.models.AuditLog;
import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByUserId(Long userId);

    List<AuditLog> findByAction(String action);

}
