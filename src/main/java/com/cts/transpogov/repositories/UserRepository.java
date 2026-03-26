package com.cts.transpogov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.transpogov.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
