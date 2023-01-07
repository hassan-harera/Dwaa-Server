package com.harera.hayat.stub.donation.food;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harera.hayat.model.donation.Donation;
import com.harera.hayat.model.donation.food.FoodDonation;
import com.harera.hayat.model.food.FoodUnit;
import com.harera.hayat.repository.donation.food.FoodDonationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FoodDonationStubs {

    private final FoodDonationRepository foodDonationRepository;

    public FoodDonation create(FoodUnit unit, Float amount,
                    OffsetDateTime foodExpirationDate, Donation donation) {
        FoodDonation foodDonation = new FoodDonation();
        foodDonation.setId(0L);
        foodDonation.setUnit(unit);
        foodDonation.setAmount(amount);
        foodDonation.setFoodExpirationDate(foodExpirationDate);
        foodDonation.setDonation(donation);
        return foodDonation;
    }

    public FoodDonation insert(FoodUnit unit, Float amount,
                    OffsetDateTime foodExpirationDate, Donation donation) {
        FoodDonation foodDonation = create(unit, amount, foodExpirationDate, donation);
        foodDonation.setId(0L);
        return foodDonationRepository.save(foodDonation);
    }

    public FoodDonation get(Long id) {
        return foodDonationRepository.findById(id).orElse(null);
    }
}
