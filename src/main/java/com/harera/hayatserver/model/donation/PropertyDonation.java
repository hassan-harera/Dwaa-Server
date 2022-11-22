package com.harera.hayatserver.model.donation;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.ZonedDateTime;

import com.harera.hayatserver.model.BaseEntity;

@Setter
@Getter
@Entity
@Table(name = "donation_residence")
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
