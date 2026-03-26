package com.cts.transpogov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.transpogov.models.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}