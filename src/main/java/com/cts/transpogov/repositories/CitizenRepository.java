package com.cts.transpogov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.transpogov.models.User;

public interface CitizenRepository extends JpaRepository<User, Long> {

}
