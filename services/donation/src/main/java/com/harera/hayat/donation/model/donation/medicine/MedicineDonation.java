package com.harera.hayat.donation.model.donation.medicine;

import java.time.OffsetDateTime;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.harera.hayat.common.model.medicine.Medicine;
import com.harera.hayat.common.model.medicine.MedicineUnit;
import com.harera.hayat.donation.model.donation.Donation;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document("medicine_donation")
public class MedicineDonation extends Donation {

    @Field(name = "amount")
    private Float amount;

    @Field(name = "unit")
    private MedicineUnit medicineUnit;

    @Field(name = "medicine_expiration_date")
    private OffsetDateTime medicineExpirationDate;

    @Field(name = "medicine")
    private Medicine medicine;
}
