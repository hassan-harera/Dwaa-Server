package com.harera.hayat.model.donation.food;

import java.time.OffsetDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.harera.hayat.model.donation.Donation;
import com.harera.hayat.model.food.FoodUnit;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "food_donation")
public class FoodDonation extends Donation {

    @Basic
    @Column(name = "food_expiration_date")
    private OffsetDateTime foodExpirationDate;

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private FoodUnit unit;

    @Basic
    @Column(name = "amount")
    private Float amount;

    @OneToOne
    @JoinColumn(name = "donation_id", referencedColumnName = "id")
    private Donation donation;
}
