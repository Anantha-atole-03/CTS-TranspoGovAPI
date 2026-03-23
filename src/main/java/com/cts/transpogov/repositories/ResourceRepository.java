package com.cts.transpogov.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.transpogov.models.Resource;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
	List<Resource> findByProgramProgramId(Long programId);
}
