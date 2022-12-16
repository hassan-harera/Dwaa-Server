package com.harera.hayat.repository.donation;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harera.hayat.model.donation.Donation;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

}