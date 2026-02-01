package com.oswaldo.payments.controller;

import com.oswaldo.payments.model.Payment;
import com.oswaldo.payments.repository.PaymentRepository;
import com.oswaldo.payments.service.PaymentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    PaymentService paymentService;
    @GetMapping("/list")
    public List<Payment> list(Authentication auth) {
        return paymentService.list(auth);
    }

    @PostMapping("add")
    @ResponseStatus(HttpStatus.CREATED)
    public Payment create(@Valid @RequestBody PaymentRequest req, Authentication auth) {

        return paymentService.create(req, auth);
    }

    public record PaymentRequest(@Min(0) double amount) {}
}
