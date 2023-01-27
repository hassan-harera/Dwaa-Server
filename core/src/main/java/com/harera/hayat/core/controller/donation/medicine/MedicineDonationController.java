package com.harera.hayat.core.controller.donation.medicine;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harera.hayat.core.model.donation.medicine.MedicineDonationRequest;
import com.harera.hayat.core.model.donation.medicine.MedicineDonationResponse;
import com.harera.hayat.core.service.donation.medicine.MedicineDonationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/v1/donations/medicine")
public class MedicineDonationController {

    private final MedicineDonationService donationService;

    public MedicineDonationController(MedicineDonationService donationService) {
        this.donationService = donationService;
    }

    @PostMapping
    @Operation(summary = "Create", description = "Create a food donations",
                    tags = "Food-Donation", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<MedicineDonationResponse> create(
                    @RequestBody MedicineDonationRequest medicineDonationRequest) {
        MedicineDonationResponse donationResponse =
                        donationService.create(medicineDonationRequest);
        return ResponseEntity.ok(donationResponse);
    }
}
