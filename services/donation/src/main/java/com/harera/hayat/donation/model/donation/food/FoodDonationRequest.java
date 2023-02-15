package com.harera.hayat.donation.model.donation.food;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true,
                value = { "id", "active", "user", "city", "unit", "category" })
public class FoodDonationRequest extends FoodDonationDto {


}
