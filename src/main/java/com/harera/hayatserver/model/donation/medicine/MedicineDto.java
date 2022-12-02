package com.harera.hayatserver.model.donation.medicine;

import com.harera.hayatserver.model.BaseEntityDto;

import lombok.Data;

@Data
public class MedicineDto extends BaseEntityDto {

    private MedicineCategoryDto category;
    private MedicineUnit unit;
    private String name;
}
