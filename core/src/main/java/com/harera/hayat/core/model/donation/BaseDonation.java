package com.harera.hayat.core.model.donation;

import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import com.harera.hayat.core.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public class BaseDonation extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "donation_id", referencedColumnName = "id")
    private com.harera.hayat.core.model.donation.Donation donation;
}
