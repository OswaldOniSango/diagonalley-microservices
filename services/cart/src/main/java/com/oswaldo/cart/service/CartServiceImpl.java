package com.oswaldo.cart.service;

import com.oswaldo.cart.controller.CartController;
import com.oswaldo.cart.controller.OrderClient;
import com.oswaldo.cart.model.CartItem;
import com.oswaldo.cart.repository.CartRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository repo;
    private final OrderClient orderClient;

    public CartServiceImpl(CartRepository repo, OrderClient orderClient) {
        this.repo = repo;
        this.orderClient = orderClient;
    }

    @Override
    public List<CartItem> list(Authentication auth) {
        return repo.findByUserEmail(auth.getName());
    }

    @Override
    public CartItem add(CartController.AddItemRequest req, Authentication auth) {
        CartItem item = new CartItem();
        item.setUserEmail(auth.getName());
        item.setProductId(req.productId());
        item.setQuantity(req.quantity());
        item.setUnitPrice(req.unitPrice());
        return repo.save(item);
    }

    @Override
    public CartItem update(Long productId, CartController.UpdateItemRequest req, Authentication auth) {
        var items = repo.findByUserEmail(auth.getName());
        CartItem item = items.stream().filter(i -> i.getProductId().equals(productId)).findFirst().orElseThrow();
        item.setQuantity(req.quantity());
        item.setUnitPrice(req.unitPrice());
        return repo.save(item);
    }

    @Override
    public void remove(Long productId, Authentication auth) {
        repo.deleteByUserEmailAndProductId(auth.getName(), productId);
    }

    @Override
    public Object checkout(Authentication auth) {
        List<CartItem> items = repo.findByUserEmail(auth.getName());
        double total = items.stream().mapToDouble(i -> i.getQuantity() * i.getUnitPrice()).sum();
        var orderItems = items.stream()
                .map(i -> new OrderClient.CreateOrderItem(i.getProductId(), i.getQuantity(), i.getUnitPrice()))
                .toList();
        var resp = orderClient.createOrder(new OrderClient.CreateOrderRequest(total, orderItems));
        repo.deleteAll(items);
        return resp;
    }
}
