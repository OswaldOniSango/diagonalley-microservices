package com.oswaldo.offers.controller;

import com.oswaldo.offers.OffersApplication;
import com.oswaldo.offers.model.Offer;
import com.oswaldo.offers.repository.OfferRepository;
import com.oswaldo.offers.service.OffersService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offers")
public class OfferController {

    @Autowired
    OffersService offerService;

    @GetMapping("/list")
    public List<Offer> list() {
        return offerService.list();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Offer create(@Valid @RequestBody OfferRequest req) {
        return offerService.create(req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        offerService.delete(id);
    }

    public record OfferRequest(@Min(1) long productId, @Min(1) @Max(90) double discountPercent) {}
}
