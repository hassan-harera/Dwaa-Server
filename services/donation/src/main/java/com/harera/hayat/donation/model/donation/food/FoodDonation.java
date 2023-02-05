package com.harera.hayat.donation.model.donation.food;

import java.time.OffsetDateTime;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.harera.hayat.common.model.food.FoodUnit;
import com.harera.hayat.donation.model.donation.Donation;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document("food_donation")
public class FoodDonation extends Donation {

    @Field(name = "food_expiration_date")
    private OffsetDateTime foodExpirationDate;

    @Field(name = "unit")
    private FoodUnit unit;

    @Field(name = "amount")
    private Float amount;
}
