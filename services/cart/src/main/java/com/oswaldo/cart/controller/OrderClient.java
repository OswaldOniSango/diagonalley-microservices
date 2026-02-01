package com.oswaldo.cart.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "orders", url = "${orders.url:http://orders:8080}")
public interface OrderClient {
    @PostMapping("/orders")
    Object createOrder(@RequestBody CreateOrderRequest req);

    record CreateOrderItem(long productId, int quantity, double unitPrice) {}
    record CreateOrderRequest(double totalAmount, List<CreateOrderItem> items) {}
}
