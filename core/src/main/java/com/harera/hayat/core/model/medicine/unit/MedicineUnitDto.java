package com.harera.hayat.core.model.medicine.unit;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.core.model.BaseEntityDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MedicineUnitDto extends BaseEntityDto {

    @JsonProperty(value = "arabic_name")
    private String arabicName;

    @JsonProperty(value = "english_name")
    private String englishName;
}
