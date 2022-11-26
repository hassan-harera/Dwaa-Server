package com.harera.hayatserver.model.donation;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayatserver.model.BaseEntityDto;
import com.harera.hayatserver.model.category.DonationCategory;
import com.harera.hayatserver.model.city.City;
import com.harera.hayatserver.model.user.User;

import lombok.Data;

@Data
public class DonationDto extends BaseEntityDto {

    private String title;
    private String description;
    @JsonProperty(value = "donation_date")
    private ZonedDateTime donationDate;
    @JsonProperty(value = "expiration_date")
    private ZonedDateTime expirationDate;
    private DonationCategory category;
    private DonationState state;
    @JsonProperty("communication_method")
    private CommunicationMethod communicationMethod;
    private City city;
    private User user;
}
