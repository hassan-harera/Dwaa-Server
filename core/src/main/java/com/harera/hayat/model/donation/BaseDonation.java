package com.harera.hayat.model.donation;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import com.harera.hayat.model.BaseEntity;

@Setter
@Getter
@MappedSuperclass
public class BaseDonation extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "donation_id", referencedColumnName = "id")
    private Donation donation;
}
