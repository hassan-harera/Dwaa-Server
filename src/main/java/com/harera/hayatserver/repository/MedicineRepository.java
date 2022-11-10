package com.harera.hayatserver.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harera.hayatserver.model.entity.MedicineEntity;

@Repository
public interface MedicineRepository extends JpaRepository<MedicineEntity, Integer> {


}
