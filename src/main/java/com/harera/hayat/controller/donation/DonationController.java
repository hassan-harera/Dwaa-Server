package com.harera.hayat.controller.donation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harera.hayat.model.donation.DonationResponse;
import com.harera.hayat.model.donation.property.PropertyDonationRequest;
import com.harera.hayat.service.donation.DonationService;

@RestController
@RequestMapping("/api/v1/donations")
public class DonationController {

    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @PostMapping("/property/donate")
    public ResponseEntity<DonationResponse> donateProperty(
                    PropertyDonationRequest propertyDonationRequest) {
        return donationService.donateProperty(propertyDonationRequest);
    }
}
