package com.harera.hayatserver.model.donation;

import lombok.Data;

import java.time.ZonedDateTime;

import com.harera.hayatserver.model.BaseEntityDto;
import com.harera.hayatserver.model.category.Category;
import com.harera.hayatserver.model.city.City;
import com.harera.hayatserver.model.user.User;

@Data
public class DonationDto extends BaseEntityDto {

    private String title;
    private String description;
    private ZonedDateTime donationDate;
    private ZonedDateTime expirationDate;
    private Category category;
    private DonationState donationState;
    private CommunicationType communicationType;
    private City city;
    private User user;
}
