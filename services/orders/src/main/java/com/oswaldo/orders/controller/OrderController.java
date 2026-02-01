package com.oswaldo.orders.controller;

import com.oswaldo.orders.model.Order;
import com.oswaldo.orders.model.OrderItem;
import com.oswaldo.orders.repository.OrderItemRepository;
import com.oswaldo.orders.repository.OrderRepository;
import com.oswaldo.orders.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping
    public List<Order> list(Authentication auth) {
        return orderService.list(auth);
    }

    @GetMapping("/{id}")
    public OrderDetail get(@PathVariable Long id, Authentication auth) {
        return orderService.get(id, auth);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(@Valid @RequestBody CreateOrderRequest req, Authentication auth) {
       return orderService.create(req, auth);
    }

    public record CreateOrderItem(@Min(1) long productId, @Min(1) int quantity, @Min(0) double unitPrice) {}
    public record CreateOrderRequest(@Min(0) double totalAmount, @NotEmpty List<CreateOrderItem> items) {}
    public record OrderDetail(Order order, List<OrderItem> items) {}
}
