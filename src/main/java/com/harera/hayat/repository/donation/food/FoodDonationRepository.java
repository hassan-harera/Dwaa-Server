package com.harera.hayat.repository.donation.food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harera.hayat.model.donation.food.FoodDonation;

@Repository
public interface FoodDonationRepository extends JpaRepository<FoodDonation, Long> {

}
