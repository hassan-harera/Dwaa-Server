package com.harera.hayat.donation.model.food;

import javax.persistence.Column;

import org.springframework.data.mongodb.core.mapping.Document;

import com.harera.hayat.common.model.BaseDocument;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document(collation = "food_unit")
public class FoodUnit extends BaseDocument {

    @Column(name = "arabic_name")
    private String arabicName;

    @Column(name = "english_name")
    private String englishName;

    @Column(name = "description")
    private String description;
}
