package com.oswaldo.payments.service;

import com.oswaldo.payments.controller.PaymentController;
import com.oswaldo.payments.model.Payment;
import com.oswaldo.payments.repository.PaymentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    PaymentRepository repo;

    public PaymentServiceImpl(PaymentRepository repo) {
        this.repo = repo;
    }


    @Override
    public List<Payment> list(Authentication auth) {
        return repo.findByUserEmail(auth.getName());
    }

    @Override
    public Payment create(PaymentController.PaymentRequest req, Authentication auth) {
        Payment p = new Payment();
        p.setUserEmail(auth.getName());
        p.setAmount(req.amount());
        p.setStatus("PAID");
        p.setCreatedAt(Instant.now());
        return repo.save(p);
    }
}
