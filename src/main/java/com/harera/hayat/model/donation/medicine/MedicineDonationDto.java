package com.harera.hayat.model.donation.medicine;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.model.donation.DonationDto;
import com.harera.hayat.model.medicine.MedicineUnitDto;

import lombok.Data;

@Data
public class MedicineDonationDto extends DonationDto {

    private Float amount;
    private MedicineUnitDto unit;
    @JsonProperty("medicine_expiration_date")
    private OffsetDateTime medicineExpirationDate;
    private MedicineDto medicine;
}
