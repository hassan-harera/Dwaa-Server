package com.harera.hayat.model.food;

import javax.persistence.Column;

import com.harera.hayat.model.BaseEntityDto;

import lombok.Data;

@Data
public class FoodUnitDto extends BaseEntityDto {
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
}
