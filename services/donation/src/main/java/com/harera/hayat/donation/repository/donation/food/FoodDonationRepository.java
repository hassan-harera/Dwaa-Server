package com.harera.hayat.donation.repository.donation.food;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.harera.hayat.donation.model.donation.food.FoodDonation;


@Repository
public interface FoodDonationRepository extends MongoRepository<FoodDonation, Long> {

}
