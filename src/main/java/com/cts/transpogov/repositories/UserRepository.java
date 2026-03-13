package com.cts.transpogov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.transpogov.models.CitizenDocument;



public interface UserRepository extends JpaRepository<CitizenDocument, Long> {

}
