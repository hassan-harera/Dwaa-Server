package com.harera.hayat.core.model.category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.harera.hayat.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "donation_category")
public class DonationCategory extends BaseEntity {

    @Column(name = "name")
    private String name;
}
