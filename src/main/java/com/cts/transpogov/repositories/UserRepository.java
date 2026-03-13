package com.cts.transpogov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.transpogov.models.CitizenDocument;
import com.cts.transpogov.models.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
