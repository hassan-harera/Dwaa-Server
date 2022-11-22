package com.harera.hayatserver.repository.donation;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harera.hayatserver.model.donation.Donation;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

}