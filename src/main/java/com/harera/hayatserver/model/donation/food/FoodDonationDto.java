package com.harera.hayatserver.model.donation.food;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayatserver.model.donation.DonationDto;
import com.harera.hayatserver.model.food.FoodUnit;

import lombok.Data;

;

@Data
public class FoodDonationDto extends DonationDto {

    private FoodUnit unit;
    private Float amount;
    @JsonProperty(value = "food_expiration_date")
    private String foodExpirationDate;
}
