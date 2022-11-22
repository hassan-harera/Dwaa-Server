package com.harera.hayatserver.model.city;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = {"active"})
public class CityResponse extends CityDto {
}
