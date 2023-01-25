package com.harera.hayat.model.need.medicine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(value = { "id", "active", "category", "status", "need_date",
        "need_expiration_date", "user", "city" })
public class MedicineNeedRequest extends MedicineNeedDto {

    @JsonProperty("medicine_unit_id")
    private Long medicineUnitId;

    @JsonProperty("medicine_id")
    private Long medicineId;
}
