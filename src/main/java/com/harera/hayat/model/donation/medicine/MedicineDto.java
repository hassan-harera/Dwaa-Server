package com.harera.hayat.model.donation.medicine;

import com.harera.hayat.model.BaseEntityDto;
import com.harera.hayat.model.medicine.MedicineUnit;

import lombok.Data;

@Data
public class MedicineDto extends BaseEntityDto {

    private MedicineCategoryDto category;
    private MedicineUnit unit;
    private String arabicName;
}
