package com.harera.hayat.common.model.city;

import java.util.List;

import com.harera.hayat.common.model.BaseEntityDto;

import lombok.Data;

@Data
public class StateDto extends BaseEntityDto {

    private String arabicName;
    private String englishName;
    private List<CityResponse> cities;
}
