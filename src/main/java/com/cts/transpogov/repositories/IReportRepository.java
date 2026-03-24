package com.cts.transpogov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cts.transpogov.models.Report;

@Repository
public interface IReportRepository extends JpaRepository<Report, Long> {

}	