package com.harera.hayat.repository.donation.food;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.harera.hayat.model.donation.food.FoodDonation;

@Repository
public interface FoodDonationRepository extends JpaRepository<FoodDonation, Long> {

    @Query("select d from FoodDonation d where d.donation.id = :id")
    Optional<FoodDonation> findByDonationId(Long id);
}
