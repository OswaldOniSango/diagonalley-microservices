package com.oswaldo.products.service;

import com.oswaldo.products.controller.ProductController;
import com.oswaldo.products.model.Product;
import com.oswaldo.products.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repo;

    public ProductServiceImpl(ProductRepository repo) {
        this.repo = repo;
    }
    @Override
    public List<Product> list() {
        return repo.findAll();
    }

    @Override
    public Product get(Long id) {
        return repo.findById(id).orElse(new Product());
    }

    @Override
    public Product create(ProductController.ProductRequest req) {
        Product p = new Product();
        p.setName(req.name());
        p.setDescription(req.description());
        p.setPrice(req.price());
        p.setStock(req.stock());
        return repo.save(p);
    }

    @Override
    public Product update(Long id, ProductController.ProductRequest req) {
        Product p = repo.findById(id).orElseThrow();
        p.setName(req.name());
        p.setDescription(req.description());
        p.setPrice(req.price());
        p.setStock(req.stock());
        return repo.save(p);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
