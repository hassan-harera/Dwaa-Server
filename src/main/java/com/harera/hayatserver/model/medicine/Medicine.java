package com.harera.hayatserver.model.medicine;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.harera.hayatserver.model.BaseEntity;

@Setter
@Getter
@Entity
@Table(name = "medicine")
public class Medicine extends BaseEntity {


    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private MedicineCategory medicineCategory;

    @ManyToOne(optional = false)
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private MedicineUnit medicineUnit;

    @Column(name = "name")
    private String name;
}
