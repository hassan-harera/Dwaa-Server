package com.harera.hayat.core.repository.medicine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harera.hayat.model.medicine.unit.MedicineUnit;

@Repository
public interface MedicineUnitRepository extends JpaRepository<MedicineUnit, Long> {

}
