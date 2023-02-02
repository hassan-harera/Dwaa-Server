package com.harera.hayat.model.donation.property;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.harera.hayat.model.donation.Donation;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "property_donation")
public class PropertyDonation extends Donation {

    @Column(name = "rooms")
    private int rooms;

    @Column(name = "bathrooms")
    private int bathrooms;

    @Column(name = "kitchens")
    private int kitchens;

    @Column(name = "people_capacity")
    private int peopleCapacity;

    @Column(name = "available_from")
    private OffsetDateTime availableFrom;

    @Column(name = "available_to")
    private OffsetDateTime availableTo;
}
