package com.harera.hayat.donation.model.donation.property;

import java.time.OffsetDateTime;

import javax.persistence.Column;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.harera.hayat.donation.model.donation.Donation;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Document("property_donation")
public class PropertyDonation extends Donation {

    @Field(name = "rooms")
    private int rooms;

    @Field(name = "bathrooms")
    private int bathrooms;

    @Field(name = "kitchens")
    private int kitchens;

    @Field(name = "people_capacity")
    private int peopleCapacity;

    @Field(name = "available_from")
    private OffsetDateTime availableFrom;

    @Field(name = "available_to")
    private OffsetDateTime availableTo;
}
