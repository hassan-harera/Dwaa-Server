package com.harera.hayat.core.model.donation.food;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true,
                value = { "id", "active", "user", "city", "unit", "category", "images" })
public class FoodDonationRequest
                extends com.harera.hayat.core.model.donation.food.FoodDonationDto {


}
