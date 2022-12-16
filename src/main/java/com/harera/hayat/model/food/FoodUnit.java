package com.harera.hayat.model.food;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.harera.hayat.model.BaseEntity;

@Setter
@Getter
@Entity
@Table(name = "food_unit")
public class FoodUnit extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
