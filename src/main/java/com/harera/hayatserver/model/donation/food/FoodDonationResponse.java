package com.harera.hayatserver.model.donation.food;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayatserver.model.donation.Donation;
import com.harera.hayatserver.model.donation.medicine.Medicine;

import lombok.Data;

@Data
public class FoodDonationResponse {

    private long id;
    private String title;
    private String description;
    private long unit;
    private Float amount;
    @JsonProperty("expiration_date")
    private ZonedDateTime expirationDate;
    private Donation donation;
    private Medicine medicine;
}
