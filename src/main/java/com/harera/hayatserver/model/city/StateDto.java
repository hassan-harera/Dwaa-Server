package com.harera.hayatserver.model.city;

import lombok.Data;

import java.util.List;

import com.harera.hayatserver.model.BaseEntityDto;

@Data
public class StateDto extends BaseEntityDto {

    private String arabicName;
    private String englishName;
    private List<CityResponse> cities;
}
