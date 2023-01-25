package com.harera.hayat.model.need.medicine;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.harera.hayat.model.BaseEntity;
import com.harera.hayat.model.medicine.Medicine;
import com.harera.hayat.model.medicine.unit.MedicineUnit;
import com.harera.hayat.model.need.Need;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "medicine_needs")
public class MedicineNeed extends BaseEntity {

    private Float amount;

    @ManyToOne
    @JoinColumn(name = "medicine_unit_id", referencedColumnName = "id")
    private MedicineUnit unit;

    @ManyToOne
    @JoinColumn(name = "medicine_id", referencedColumnName = "id")
    private Medicine medicine;

    @OneToOne
    @JoinColumn(name = "need_id", referencedColumnName = "id")
    private Need need;
}
