package com.harera.hayatserver.model.donation.medicine;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.harera.hayatserver.model.BaseEntity;
import com.harera.hayatserver.model.donation.Donation;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "donation_medicine")
public class MedicineDonation extends BaseEntity {

    @Column(name = "amount")
    private Float amount;

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private MedicineUnit medicineUnit;

    @Column(name = "medicine_expiration_date")
    private ZonedDateTime medicineExpirationDate;

    @OneToOne
    @JoinColumn(name = "donation_id", referencedColumnName = "id")
    private Donation donation;

    @ManyToOne
    @JoinColumn(name = "medicine_id", referencedColumnName = "id")
    private Medicine medicine;
}
