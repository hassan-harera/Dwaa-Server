package com.harera.hayat.repository.need.medicine;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harera.hayat.model.need.medicine.MedicineNeed;

public interface MedicineNeedRepository extends JpaRepository<MedicineNeed, Long> {
}