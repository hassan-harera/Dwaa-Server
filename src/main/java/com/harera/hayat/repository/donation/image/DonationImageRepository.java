package com.harera.hayat.repository.donation.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harera.hayat.model.donation.image.DonationImage;

@Repository
public interface DonationImageRepository extends JpaRepository<DonationImage, Long> {
}
