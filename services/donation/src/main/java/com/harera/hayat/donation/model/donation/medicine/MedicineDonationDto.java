package com.harera.hayat.donation.model.donation.medicine;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.common.model.medicine.MedicineDto;
import com.harera.hayat.common.model.medicine.MedicineUnitDto;
import com.harera.hayat.donation.model.donation.DonationDto;

import lombok.Data;

@Data
public class MedicineDonationDto extends DonationDto {

    private Float amount;

    private MedicineUnitDto unit;

    @JsonProperty("medicine_expiration_date")
    private OffsetDateTime medicineExpirationDate;

    private MedicineDto medicine;
}
