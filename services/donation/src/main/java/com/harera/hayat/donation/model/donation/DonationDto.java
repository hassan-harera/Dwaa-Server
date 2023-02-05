package com.harera.hayat.donation.model.donation;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.common.model.BaseEntityDto;
import com.harera.hayat.common.model.city.CityDto;
import com.harera.hayat.common.model.user.UserDto;
import com.harera.hayat.donation.model.donation.image.DonationImageDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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

    @JsonProperty("city_id")
    private Long cityId;

    private CityDto city;

    private UserDto user;

    private List<DonationImageDto> images;
}
