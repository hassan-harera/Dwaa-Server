package com.harera.hayatserver.model.donation;

import java.time.ZonedDateTime;

import com.harera.hayatserver.model.BaseEntityDto;
import com.harera.hayatserver.model.category.DonationCategory;
import com.harera.hayatserver.model.city.City;
import com.harera.hayatserver.model.user.User;

import lombok.Data;

@Data
public class DonationDto extends BaseEntityDto {

    private String title;
    private String description;
    private ZonedDateTime donationDate;
    private ZonedDateTime expirationDate;
    private DonationCategory category;
    private DonationState state;
    private CommunicationMethod communicationMethod;
    private City city;
    private User user;
}
