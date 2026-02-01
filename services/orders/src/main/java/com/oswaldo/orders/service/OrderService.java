package com.oswaldo.orders.service;

import com.oswaldo.orders.controller.OrderController;
import com.oswaldo.orders.model.Order;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface OrderService {

    List<Order> list(Authentication auth);

    OrderController.OrderDetail get(Long id, Authentication auth);

    Order create(OrderController.CreateOrderRequest req, Authentication auth);
}
