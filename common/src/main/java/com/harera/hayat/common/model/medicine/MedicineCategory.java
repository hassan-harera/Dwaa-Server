package com.harera.hayat.common.model.medicine;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.harera.hayat.common.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "medicine_category")
public class MedicineCategory extends BaseEntity {

    @Basic
    @Column(name = "arabic_name")
    private String arabicName;

    @Basic
    @Column(name = "english_name")
    private String englishName;
}