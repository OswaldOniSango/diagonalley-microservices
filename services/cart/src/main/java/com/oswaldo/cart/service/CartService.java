package com.oswaldo.cart.service;

import com.oswaldo.cart.controller.CartController;
import com.oswaldo.cart.model.CartItem;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CartService {

    List<CartItem> list(Authentication auth);

    CartItem add(CartController.AddItemRequest req, Authentication auth);

    CartItem update( Long productId, CartController.UpdateItemRequest req, Authentication auth);

    void remove(Long productId, Authentication auth);

    Object checkout(Authentication auth);
}
