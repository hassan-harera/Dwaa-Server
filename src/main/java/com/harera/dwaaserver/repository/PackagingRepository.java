package com.harera.dwaaserver.repository;


import com.harera.dwaaserver.entity.PackagingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackagingRepository extends JpaRepository<PackagingEntity, Integer> {


}
