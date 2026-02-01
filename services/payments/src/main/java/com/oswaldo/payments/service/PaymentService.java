package com.oswaldo.payments.service;

import com.oswaldo.payments.controller.PaymentController;
import com.oswaldo.payments.model.Payment;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface PaymentService {

    List<Payment> list(Authentication auth);

    Payment create(PaymentController.PaymentRequest req, Authentication auth);

}
