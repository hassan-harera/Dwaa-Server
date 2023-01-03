package com.harera.hayat.model.donation.food;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.model.donation.DonationDto;
import com.harera.hayat.model.food.FoodUnitDto;

import lombok.Data;

@Data
public class FoodDonationDto extends DonationDto {

    private FoodUnitDto unit;

    private Float amount;

    @JsonProperty(value = "food_expiration_date")
    private ZonedDateTime foodExpirationDate;
}
