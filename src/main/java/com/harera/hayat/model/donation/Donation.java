package com.harera.hayat.model.donation;

import java.time.ZonedDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.harera.hayat.model.BaseEntity;
import com.harera.hayat.model.city.City;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "donation")
@Setter
@Getter
public class Donation extends BaseEntity {

    @Basic
    @Column(name = "title")
    private String title;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "date")
    private ZonedDateTime donationDate;

    @Basic
    @Column(name = "expiration_date")
    private ZonedDateTime availableTo;

    @Column(name = "communication_method")
    private CommunicationMethod communicationMethod;

    @Column(name = "category")
    private DonationCategory category;

    @Column(name = "state")
    private DonationState state;

    @ManyToOne(targetEntity = City.class)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    //    @ManyToOne(targetEntity = User.class)
    //    @JoinColumn(name = "uid", referencedColumnName = "id")
    //    private User user;
}
