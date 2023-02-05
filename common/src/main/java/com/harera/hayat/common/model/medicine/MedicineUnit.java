package com.harera.hayat.common.model.medicine;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.harera.hayat.common.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "medicine_unit")
public class MedicineUnit extends BaseEntity {

    @Basic
    @Column(name = "arabic_name")
    private String arabicName;

    @Basic
    @Column(name = "english_name")
    private String englishName;
}
