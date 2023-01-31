package com.harera.hayat.model.city;

import lombok.Data;

import java.util.List;

import com.harera.hayat.model.BaseEntityDto;

@Data
public class StateDto extends BaseEntityDto {

    private String arabicName;
    private String englishName;
    private List<CityResponse> cities;
}
