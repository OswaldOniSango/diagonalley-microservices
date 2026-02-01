package com.oswaldo.offers.service;

import com.oswaldo.offers.controller.OfferController;
import com.oswaldo.offers.model.Offer;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface OffersService {
    List<Offer> list();

    Offer create(@Valid @RequestBody OfferController.OfferRequest req);

    void delete(@PathVariable Long id);
}
