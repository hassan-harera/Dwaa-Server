package com.harera.hayat.service.donation;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DonationScheduleService {

    private final DonationService donationService;

    public DonationScheduleService(DonationService donationService) {
        this.donationService = donationService;
    }

    @Scheduled(cron = "0 0 * * *")
    public void checkExpiredDonations() {
        donationService.deactivateExpiredDonations();
    }
}
