package com.harera.hayat.common.model.medicine;


import com.harera.hayat.common.model.BaseEntityDto;

import lombok.Data;

@Data
public class MedicineDto extends BaseEntityDto {

    private MedicineCategoryDto category;
    private MedicineUnit unit;
    private String arabicName;
}
