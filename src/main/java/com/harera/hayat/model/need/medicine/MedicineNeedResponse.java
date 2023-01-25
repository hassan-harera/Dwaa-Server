package com.harera.hayat.model.need.medicine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.model.medicine.MedicineDto;
import com.harera.hayat.model.medicine.unit.MedicineUnitDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(value = { "user_id", "city_id" })
public class MedicineNeedResponse extends MedicineNeedDto {

    @JsonProperty("unit")
    private MedicineUnitDto medicineUnit;

    @JsonProperty("medicine")
    private MedicineDto medicine;
}
