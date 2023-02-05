package com.harera.hayat.donation.repository.donation.medicine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harera.hayat.donation.model.donation.medicine.MedicineDonation;

@Repository
public interface MedicineDonationRepository
                extends JpaRepository<MedicineDonation, Long> {

}
