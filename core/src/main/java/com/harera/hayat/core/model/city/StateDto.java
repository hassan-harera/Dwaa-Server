package com.harera.hayat.core.model.city;

import java.util.List;

import com.harera.hayat.core.model.BaseEntityDto;

import lombok.Data;

@Data
public class StateDto extends BaseEntityDto {

    private String arabicName;
    private String englishName;
    private List<com.harera.hayat.core.model.city.CityResponse> cities;
}
