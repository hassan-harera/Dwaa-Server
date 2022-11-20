package com.harera.hayatserver.model.donation;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.harera.hayatserver.model.BaseEntity;

@Entity
@Table(name = "donation_state", schema = "public", catalog = "Dwaa")
@Setter
@Getter
public class DonationState extends BaseEntity {

    @Basic
    @Column(name = "donation_state")
    private String donationState;
}
