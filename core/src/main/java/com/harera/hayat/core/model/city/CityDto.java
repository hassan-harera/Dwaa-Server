package com.harera.hayat.core.model.city;

import com.harera.hayat.core.model.BaseEntityDto;

import lombok.Data;

@Data
public class CityDto extends BaseEntityDto {

    private String arabicName;
    private String englishName;
}
