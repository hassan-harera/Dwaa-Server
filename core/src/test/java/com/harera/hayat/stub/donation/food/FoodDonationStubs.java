package com.harera.hayat.stub.donation.food;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harera.hayat.model.city.City;
import com.harera.hayat.model.donation.DonationCategory;
import com.harera.hayat.model.donation.DonationState;
import com.harera.hayat.model.donation.food.FoodDonation;
import com.harera.hayat.model.food.FoodUnit;
import com.harera.hayat.repository.donation.FoodDonationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FoodDonationStubs {

    private final FoodDonationRepository foodDonationRepository;

    public FoodDonation create(FoodUnit unit, Float amount,
                    OffsetDateTime foodExpirationDate, String title,
                    DonationCategory category, String description, City city,
                    DonationState state) {
        FoodDonation foodDonation = new FoodDonation();
        foodDonation.setId(0L);
        foodDonation.setUnit(unit);
        foodDonation.setAmount(amount);
        foodDonation.setFoodExpirationDate(foodExpirationDate);
        foodDonation.setTitle(title);
        foodDonation.setCategory(category);
        foodDonation.setCity(city);
        foodDonation.setState(state);
        return foodDonation;
    }

    public FoodDonation insert(FoodUnit unit, Float amount,
                    OffsetDateTime foodExpirationDate, String title,
                    DonationCategory category, String description, City city,
                    DonationState state) {
        FoodDonation foodDonation = create(unit, amount, foodExpirationDate, title,
                        category, description, city, state);
        return foodDonationRepository.save(foodDonation);
    }

    public FoodDonation get(Long id) {
        return foodDonationRepository.findById(id).orElse(null);
    }
}
