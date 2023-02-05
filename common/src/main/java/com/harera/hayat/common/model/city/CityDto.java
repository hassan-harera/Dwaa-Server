package com.harera.hayat.common.model.city;

import com.harera.hayat.common.model.BaseEntityDto;

import lombok.Data;


@Data
public class CityDto extends BaseEntityDto {

    private String arabicName;
    private String englishName;
}
