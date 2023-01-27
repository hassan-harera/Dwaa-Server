package com.harera.hayat.core.model.donation.food;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.model.donation.DonationDto;
import com.harera.hayat.model.food.FoodUnitDto;

import lombok.Data;

@Data
public class FoodDonationDto extends DonationDto {

    private FoodUnitDto unit;

    private Float amount;

    @JsonProperty(value = "food_expiration_date")
    private OffsetDateTime foodExpirationDate;

    @JsonProperty(value = "city_id")
    private Long cityId;

    @JsonProperty(value = "unit_id")
    private Long unitId;
}
