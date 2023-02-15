package com.harera.hayat.donation.model.donation;

import java.time.OffsetDateTime;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.harera.hayat.common.model.BaseDocument;
import com.harera.hayat.common.model.city.City;
import com.harera.hayat.common.model.user.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document
public class Donation extends BaseDocument {

    @Field(name = "title")
    private String title;

    @Field(name = "description")
    private String description;

    @Field(name = "donation_date")
    private OffsetDateTime donationDate;

    @Field(name = "expiration_date")
    private OffsetDateTime donationExpirationDate;

    private CommunicationMethod communicationMethod;

    private DonationCategory category;

    private DonationState state;

    private City city;

    private User user;
}
