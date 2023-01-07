package com.harera.hayat.model.donation.food;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true,
                value = { "active", "city_id", "unit_id", "state" })
public class FoodDonationResponse extends FoodDonationDto {
}
