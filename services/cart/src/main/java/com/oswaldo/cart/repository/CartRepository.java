package com.oswaldo.cart.repository;

import com.oswaldo.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUserEmail(String userEmail);
    void deleteByUserEmailAndProductId(String userEmail, Long productId);
}
