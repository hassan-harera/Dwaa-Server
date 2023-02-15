package com.harera.hayat.donation.repository.donation.property;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.harera.hayat.donation.model.donation.property.PropertyDonation;

@Repository
public interface PropertyDonationRepository
                extends MongoRepository<PropertyDonation, Long> {
}
