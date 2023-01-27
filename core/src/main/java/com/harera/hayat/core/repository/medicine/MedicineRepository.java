package com.harera.hayat.core.repository.medicine;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.harera.hayat.core.model.medicine.Medicine;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    @Query("SELECT m FROM Medicine m WHERE m.arabicName LIKE %?1% or m.englishName LIKE %?1%")
    List<Medicine> search(String query, Pageable pageable);
}
