package com.harera.hayatserver.model.donation.food;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

;

@Data
@JsonIgnoreProperties(ignoreUnknown = true,
                value = { "id", "active", "user", "city", "unit", "category" })
public class FoodDonationRequest extends FoodDonationDto {

    @JsonProperty(value = "city_id")
    private String cityId;

    @JsonProperty(value = "unit_id")
    private String unitId;
}
