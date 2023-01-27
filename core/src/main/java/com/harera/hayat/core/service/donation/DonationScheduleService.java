package com.harera.hayat.core.service.donation;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DonationScheduleService {

    private final com.harera.hayat.core.service.donation.DonationService donationService;

    public DonationScheduleService(
                    com.harera.hayat.core.service.donation.DonationService donationService) {
        this.donationService = donationService;
    }

    @Scheduled(cron = "0 0 * * *")
    public void checkExpiredDonations() {
        donationService.deactivateExpiredDonations();
    }
}
