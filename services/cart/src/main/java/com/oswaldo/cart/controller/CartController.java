package com.oswaldo.cart.controller;

import com.oswaldo.cart.model.CartItem;
import com.oswaldo.cart.repository.CartRepository;
import com.oswaldo.cart.service.CartService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping
    public List<CartItem> list(Authentication auth) {
        return cartService.list(auth);
    }

    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public CartItem add(@Valid @RequestBody AddItemRequest req, Authentication auth) {
        return cartService.add(req, auth);
    }

    @PutMapping("/items/{productId}")
    public CartItem update(@PathVariable Long productId, @Valid @RequestBody UpdateItemRequest req, Authentication auth) {
        return cartService.update(productId, req, auth);
    }

    @DeleteMapping("/items/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long productId, Authentication auth) {
        cartService.remove(productId, auth);
    }

    @PostMapping("/checkout")
    public Object checkout(Authentication auth) {
        return cartService.checkout(auth);
    }

    public record AddItemRequest(@Min(1) long productId, @Min(1) int quantity, @Min(0) double unitPrice) {}
    public record UpdateItemRequest(@Min(1) int quantity, @Min(0) double unitPrice) {}
}
