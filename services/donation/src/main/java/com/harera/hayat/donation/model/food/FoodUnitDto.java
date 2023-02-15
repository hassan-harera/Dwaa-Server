package com.harera.hayat.donation.model.food;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.common.model.BaseDocumentDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FoodUnitDto extends BaseDocumentDto {

    @JsonProperty("arabic_name")
    private String arabicName;

    @JsonProperty("english_name")
    private String englishName;

    @Column(name = "description")
    private String description;
}
