package com.harera.hayatserver.model.donation.food;

import java.time.ZonedDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.harera.hayatserver.model.BaseEntity;
import com.harera.hayatserver.model.food.FoodUnit;

@Entity
@Table(name = "donation_food")
public class FoodDonation extends BaseEntity {

    @Basic
    @Column(name = "expiration_date")
    private ZonedDateTime expirationDate;

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private FoodUnit unit;

    @Basic
    @Column(name = "amount")
    private double amount;
}
