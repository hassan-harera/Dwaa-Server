package com.harera.hayat.donation.model.donation.food;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true, value = { "id", "user", "city", "unit",
        "category", "donation_date", "donation_expiration_date" })
public class FoodDonationUpdateRequest extends FoodDonationDto {

}
