package com.harera.hayat.model.donation.medicine;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.harera.hayat.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "medicine")
public class Medicine extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private MedicineCategory category;

    @ManyToOne(optional = false)
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private MedicineUnit unit;

    @Column(name = "arabic_name")
    private String arabicName;

    @Column(name = "english_name")
    private String englishName;
}
