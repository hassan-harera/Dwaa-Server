package com.harera.hayat.common.repository.medicine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harera.hayat.common.model.medicine.MedicineUnit;

@Repository
public interface MedicineUnitRepository extends JpaRepository<MedicineUnit, Long> {

}
