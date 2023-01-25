package com.harera.hayat.model.need;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.model.BaseEntityDto;
import com.harera.hayat.model.city.CityDto;
import com.harera.hayat.model.donation.CommunicationMethod;
import com.harera.hayat.model.user.UserDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NeedDto extends BaseEntityDto {

    private String title;

    private String description;

    @JsonProperty("need_date")
    private OffsetDateTime needDate;

    @JsonProperty("need_expiration_date")
    private OffsetDateTime needExpirationDate;

    private UserDto user;

    @JsonProperty("user_id")
    private Long userId;

    private CityDto city;

    @JsonProperty("city_id")
    private Long cityId;

    private NeedCategory category;

    private CommunicationMethod communicationMethod;
}
