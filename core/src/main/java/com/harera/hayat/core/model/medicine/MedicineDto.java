package com.harera.hayat.core.model.medicine;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.core.model.medicine.category.MedicineCategoryDto;
import com.harera.core.model.medicine.unit.MedicineUnitDto;
import com.harera.hayat.core.model.medicine.category.MedicineCategoryDto;
import com.harera.hayat.core.model.medicine.unit.MedicineUnitDto;
import com.harera.hayat.model.BaseEntityDto;
import com.harera.hayat.model.medicine.category.MedicineCategoryDto;
import com.harera.hayat.model.medicine.unit.MedicineUnitDto;

import lombok.Data;

@Data
public class MedicineDto extends BaseEntityDto {

    private MedicineCategoryDto category;
    private MedicineUnitDto unit;

    @JsonProperty(value = "arabic_name")
    private String arabicName;

    @JsonProperty(value = "english_name")
    private String englishName;
}
