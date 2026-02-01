package com.oswaldo.payments.repository;

import com.oswaldo.payments.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUserEmail(String userEmail);
}
