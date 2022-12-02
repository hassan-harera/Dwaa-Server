package com.harera.hayatserver.model.donation;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayatserver.model.BaseEntityDto;
import com.harera.hayatserver.model.city.CityDto;
import com.harera.hayatserver.model.user.UserDto;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = { "active", "user", "password" })
public class DonationDto extends BaseEntityDto {

    private String title;
    private String description;
    @JsonProperty(value = "donation_date")
    private ZonedDateTime donationDate;
    @JsonProperty(value = "donation_expiration_date")
    private ZonedDateTime expirationDate;
    private DonationCategory category;
    private DonationState state = DonationState.PENDING;
    @JsonProperty("communication_method")
    private CommunicationMethod communicationMethod;
    private CityDto city;
    private UserDto user;
}
