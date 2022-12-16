package com.harera.hayat.model.donation.property;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.harera.hayat.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "donation_property")
public class PropertyDonation extends BaseEntity {

    @Column(name = "rooms")
    private int rooms;

    @Column(name = "bathrooms")
    private int bathrooms;

    @Column(name = "kitchens")
    private int kitchens;

    @Column(name = "available_from")
    private ZonedDateTime availableFrom;

    @Column(name = "available_to")
    private ZonedDateTime availableTo;
}
