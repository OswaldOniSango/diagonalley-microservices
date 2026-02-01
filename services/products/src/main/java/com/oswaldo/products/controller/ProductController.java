package com.oswaldo.products.controller;

import com.oswaldo.products.model.Product;
import com.oswaldo.products.repository.ProductRepository;
import com.oswaldo.products.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("list")
    public List<Product> list() {
        return productService.list();
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable Long id) {
        return productService.get(id);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@Valid @RequestBody ProductRequest req) {
        return productService.create(req);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @Valid @RequestBody ProductRequest req) {
        return productService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    public record ProductRequest(@NotBlank String name, @NotBlank String description, @Min(0) double price, @Min(0) int stock) {}
}
