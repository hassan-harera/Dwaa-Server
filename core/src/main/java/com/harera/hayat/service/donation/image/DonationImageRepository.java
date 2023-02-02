package com.harera.hayat.service.donation.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harera.hayat.model.image.DonationImage;

@Repository
public interface DonationImageRepository extends JpaRepository<DonationImage, Long> {
}
