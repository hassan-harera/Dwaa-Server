package com.harera.hayat.model.donation.property;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.harera.hayat.config.OffsetDateTimeSerializer;
import com.harera.hayat.model.BaseEntity;

import lombok.Data;

@Data
public class PropertyDonationRequest extends BaseEntity {

    private String title;
    private String description;
    private Integer rooms;
    private Integer bathrooms;
    private Integer floors;
    private Integer kitchens;

    @JsonProperty("available_from")
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime AvailableFrom;
    @JsonProperty("available_to")
    private OffsetDateTime AvailableTo;
    @JsonProperty("city_id")
    private long cityId;
    @JsonProperty("communication_type_id")
    private long communicationTypeId;
}
