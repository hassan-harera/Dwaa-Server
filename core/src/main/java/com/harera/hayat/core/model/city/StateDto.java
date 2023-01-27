package com.harera.hayat.core.model.city;

import lombok.Data;

import java.util.List;

import com.harera.core.model.BaseEntityDto;
import com.harera.hayat.core.model.BaseEntityDto;
import com.harera.hayat.model.BaseEntityDto;

@Data
public class StateDto extends BaseEntityDto {

    private String arabicName;
    private String englishName;
    private List<CityResponse> cities;
}
