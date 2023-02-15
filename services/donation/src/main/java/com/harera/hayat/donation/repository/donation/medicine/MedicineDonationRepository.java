package com.harera.hayat.donation.repository.donation.medicine;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.harera.hayat.donation.model.donation.medicine.MedicineDonation;

@Repository
public interface MedicineDonationRepository
                extends MongoRepository<MedicineDonation, Long> {

}
