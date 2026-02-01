package com.oswaldo.offers.service;

import com.oswaldo.offers.controller.OfferController;
import com.oswaldo.offers.model.Offer;
import com.oswaldo.offers.repository.OfferRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OffersServiceImpl implements OffersService {

    final OfferRepository repo;

    public OffersServiceImpl(OfferRepository offerRepository) {
        this.repo = offerRepository;
    }

    @Override
    public List<Offer> list() {
        return repo.findAll();
    }

    @Override
    public Offer create(OfferController.OfferRequest req) {
        Offer o = new Offer();
        o.setProductId(req.productId());
        o.setDiscountPercent(req.discountPercent());
        return repo.save(o);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
