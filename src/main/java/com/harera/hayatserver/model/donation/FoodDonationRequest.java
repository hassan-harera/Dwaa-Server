package com.harera.hayatserver.model.donation;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

;

@Data
public class FoodDonationRequest {

    private String title;
    private String description;
    private long unit;
    private Float amount;
    private CommunicationType communication;
    @JsonProperty(value = "expiration_date")
    private String expirationDate;
}
