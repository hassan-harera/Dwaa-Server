package com.harera.hayat.model.donation;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.harera.hayat.config.OffsetDateTimeSerializer;
import com.harera.hayat.model.BaseEntityDto;
import com.harera.hayat.model.city.CityDto;
import com.harera.hayat.model.donation.image.DonationImageDto;
import com.harera.hayat.model.user.UserDto;

import lombok.Data;

@Data
public class DonationDto extends BaseEntityDto {

    private String title;

    private String description;

    @JsonProperty(value = "donation_date")
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime donationDate;

    @JsonProperty(value = "donation_expiration_date")
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime donationExpirationDate;

    private DonationCategory category;

    private DonationState state = DonationState.PENDING;

    @JsonProperty("communication_method")
    private CommunicationMethod communicationMethod;

    private CityDto city;

    private UserDto user;

    private List<DonationImageDto> images;
}
