package com.harera.hayat.repository.donation.medicine;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.harera.hayat.model.donation.medicine.MedicineDonation;

@Repository
public interface MedicineDonationRepository
                extends JpaRepository<MedicineDonation, Long> {

    @Query("select d from MedicineDonation d where d.donation.id = :id")
    Optional<MedicineDonation> findByDonationId(Long id);

    @Query("SELECT m FROM MedicineDonation m WHERE m.active = true")
    List<MedicineDonation> findAllActiveMedicineDonation(Pageable pageable);
}
