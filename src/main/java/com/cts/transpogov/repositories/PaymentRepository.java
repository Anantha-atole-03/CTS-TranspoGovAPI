package com.cts.transpogov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.transpogov.models.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}