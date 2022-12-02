package com.harera.hayatserver.model.donation.medicine;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.harera.hayatserver.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "medicine_category")
public class MedicineCategory extends BaseEntity {

    @Basic
    @Column(name = "name")
    private String category;
}
