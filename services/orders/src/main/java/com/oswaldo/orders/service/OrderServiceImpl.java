package com.oswaldo.orders.service;

import com.oswaldo.orders.controller.OrderController;
import com.oswaldo.orders.model.Order;
import com.oswaldo.orders.model.OrderItem;
import com.oswaldo.orders.repository.OrderItemRepository;
import com.oswaldo.orders.repository.OrderRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepo;
    private final OrderItemRepository itemRepo;

    public OrderServiceImpl(OrderRepository orderRepo, OrderItemRepository itemRepo) {
        this.orderRepo = orderRepo;
        this.itemRepo = itemRepo;
    }

    @Override
    public List<Order> list(Authentication auth) {
        return orderRepo.findByUserEmail(auth.getName());
    }

    @Override
    public OrderController.OrderDetail get(Long id, Authentication auth) {
        Order order = orderRepo.findById(id).orElseThrow();
        if (!order.getUserEmail().equals(auth.getName())) {
            throw new RuntimeException("Forbidden");
        }
        List<OrderItem> items = itemRepo.findByOrderId(order.getId());
        return new OrderController.OrderDetail(order, items);
    }

    @Override
    public Order create(OrderController.CreateOrderRequest req, Authentication auth) {
        Order order = new Order();
        order.setUserEmail(auth.getName());
        order.setTotalAmount(req.totalAmount());
        order.setStatus("CREATED");
        order.setCreatedAt(Instant.now());
        order = orderRepo.save(order);
        for (OrderController.CreateOrderItem item : req.items()) {
            OrderItem oi = new OrderItem();
            oi.setOrderId(order.getId());
            oi.setProductId(item.productId());
            oi.setQuantity(item.quantity());
            oi.setUnitPrice(item.unitPrice());
            itemRepo.save(oi);
        }
        return order;
    }
}
