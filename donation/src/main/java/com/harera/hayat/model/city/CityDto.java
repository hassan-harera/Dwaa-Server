package com.harera.hayat.model.city;

import lombok.Data;

import com.harera.hayat.model.BaseEntityDto;

@Data
public class CityDto extends BaseEntityDto {

    private String arabicName;
    private String englishName;
}
