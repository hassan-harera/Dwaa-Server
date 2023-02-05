package com.harera.hayat.donation.repository.donation.property;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harera.hayat.donation.model.donation.property.PropertyDonation;

@Repository
public interface PropertyDonationRepository
                extends JpaRepository<PropertyDonation, Long> {
}
