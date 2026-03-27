package com.cts.transpogov.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cts.transpogov.models.Audit;

public interface AuditRepository extends JpaRepository<Audit, Long> {

	@Query("""
			SELECT a.status, COUNT(a)
			FROM Audit a
			GROUP BY a.status
			""")
	List<Object[]> findStatusCount();

	long countByStatus(String status);

}
