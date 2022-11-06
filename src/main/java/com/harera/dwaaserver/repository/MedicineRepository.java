package com.harera.dwaaserver.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harera.dwaaserver.entity.MedicineEntity;

@Repository
public interface MedicineRepository extends JpaRepository<MedicineEntity, Integer> {


}
