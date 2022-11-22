package com.harera.hayatserver.model.city;

import lombok.Data;

import com.harera.hayatserver.model.BaseEntityDto;

@Data
public class CityDto extends BaseEntityDto {

    private String arabicName;
    private String englishName;
}
