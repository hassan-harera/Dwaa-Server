package com.harera.hayat.core.model.donation;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.core.model.donation.image.DonationImageDto;
import com.harera.hayat.core.model.donation.image.DonationImageDto;
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
    private OffsetDateTime donationDate;

    @JsonProperty(value = "donation_expiration_date")
    private OffsetDateTime donationExpirationDate;

    private DonationCategory category;

    private DonationState state = DonationState.PENDING;

    @JsonProperty("communication_method")
    private CommunicationMethod communicationMethod;

    private CityDto city;

    private UserDto user;

    private List<DonationImageDto> images;
}
