package com.oswaldo.offers.repository;

import com.oswaldo.offers.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {}
