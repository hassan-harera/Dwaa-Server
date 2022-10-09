package com.harera.medicine.repository;


import com.harera.medicine.entity.PackagingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackagingRepository extends JpaRepository<PackagingEntity, Integer> {


}
