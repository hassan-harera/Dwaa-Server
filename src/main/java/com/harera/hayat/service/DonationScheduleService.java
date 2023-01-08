package com.harera.hayat.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.harera.hayat.service.donation.DonationService;

@Service
public class DonationScheduleService {

    private final DonationService donationService;

    public DonationScheduleService(DonationService donationService) {
        this.donationService = donationService;
    }

    // schedule cron job to run every 24 hours at 00:00 time
    @Scheduled(cron = "0 0 * * *")
    public void checkExpiredDonations() {
        donationService.deactivateExpiredDonations();
    }
}
