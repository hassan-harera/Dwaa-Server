package com.harera.hayat.model.donation.medicine;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.harera.hayat.model.BaseEntity;
import com.harera.hayat.model.donation.Donation;
import com.harera.hayat.model.medicine.Medicine;
import com.harera.hayat.model.medicine.unit.MedicineUnit;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "medicine_donation")
public class MedicineDonation extends BaseEntity {

    @Column(name = "amount")
    private Float amount;

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private MedicineUnit unit;

    @Column(name = "medicine_expiration_date")
    private OffsetDateTime medicineExpirationDate;

    @OneToOne
    @JoinColumn(name = "donation_id", referencedColumnName = "id")
    private Donation donation;

    @ManyToOne
    @JoinColumn(name = "medicine_id", referencedColumnName = "id")
    private Medicine medicine;
}
