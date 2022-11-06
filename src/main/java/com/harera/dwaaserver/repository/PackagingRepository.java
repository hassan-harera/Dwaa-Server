package com.harera.dwaaserver.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.harera.dwaaserver.entity.PackagingEntity;

@Repository
public interface PackagingRepository extends JpaRepository<PackagingEntity, Integer> {

    @Query("SELECT p FROM PackagingEntity p")
    List<PackagingEntity> getAllPackaging();

    @Query("SELECT p FROM PackagingEntity p where p.packagingId = ?1")
    Optional<PackagingEntity> getPackaging(Integer packagingId);
}
