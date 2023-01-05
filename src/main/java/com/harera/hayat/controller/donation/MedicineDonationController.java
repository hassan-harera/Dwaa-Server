package com.harera.hayat.controller.donation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harera.hayat.model.donation.medicine.MedicineDonationRequest;
import com.harera.hayat.model.donation.medicine.MedicineDonationResponse;
import com.harera.hayat.service.donation.medicine.MedicineDonationService;

@RestController
@RequestMapping("/api/v1/donations/medicine")
public class MedicineDonationController {

    private final MedicineDonationService donationService;

    public MedicineDonationController(MedicineDonationService donationService) {
        this.donationService = donationService;
    }

    @PostMapping
    public ResponseEntity<MedicineDonationResponse> donateMedicine(
                    @RequestBody MedicineDonationRequest medicineDonationRequest) {
        MedicineDonationResponse donationResponse =
                        donationService.create(medicineDonationRequest);
        return ResponseEntity.ok(donationResponse);
    }
}
