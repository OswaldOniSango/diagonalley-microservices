package com.oswaldo.products.service;

import com.oswaldo.products.controller.ProductController;
import com.oswaldo.products.model.Product;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ProductService {

    List<Product> list();

    Product get( Long id);

    Product create(ProductController.ProductRequest req);

    Product update(Long id, ProductController.ProductRequest req);

    void delete(Long id);
}
