package com.harera.hayatserver.repository.medicine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harera.hayatserver.model.donation.medicine.MedicineUnit;

@Repository
public interface MedicineUnitRepository extends JpaRepository<MedicineUnit, Long> {

}
