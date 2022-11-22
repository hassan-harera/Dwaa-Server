package com.harera.hayatserver.model.medicine;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.harera.hayatserver.model.BaseEntity;

@Setter
@Getter
@Entity
@Table(name = "medicine_category")
public class MedicineCategory extends BaseEntity {

    @Basic
    @Column(name = "name")
    private String category;
}
