package com.harera.hayat.model.donation.medicine;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.harera.hayat.config.OffsetDateTimeSerializer;
import com.harera.hayat.model.donation.DonationDto;
import com.harera.hayat.model.medicine.MedicineDto;
import com.harera.hayat.model.medicine.unit.MedicineUnitDto;

import lombok.Data;

@Data
public class MedicineDonationDto extends DonationDto {

    private Float amount;
    private MedicineUnitDto unit;
    @JsonProperty("medicine_expiration_date")
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime medicineExpirationDate;
    private MedicineDto medicine;
}
