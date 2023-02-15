package com.harera.hayat.donation.model.donation.food;

import java.time.OffsetDateTime;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.harera.hayat.donation.model.donation.Donation;
import com.harera.hayat.donation.model.food.FoodUnit;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document("food_donation")
public class FoodDonation extends Donation {

    private OffsetDateTime foodExpirationDate;

    private FoodUnit unit;

    @Field(name = "amount")
    private Float amount;
}
