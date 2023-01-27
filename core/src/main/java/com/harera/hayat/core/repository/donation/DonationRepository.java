package com.harera.hayat.core.repository.donation;


import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.harera.hayat.core.model.donation.Donation;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query("select d from Donation d where d.active = true and date(d.donationExpirationDate) = date(?1)")
    List<Donation> findAllActiveByExpirationDate(OffsetDateTime now);

}