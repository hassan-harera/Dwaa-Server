package com.harera.hayat.core.model.city;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = {"active"})
public class CityResponse extends com.harera.hayat.core.model.city.CityDto {
}
