package com.harera.hayatserver.controller.donations;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.harera.hayatserver.model.donation.DonationResponse;
import com.harera.hayatserver.model.donation.food.FoodDonationRequest;
import com.harera.hayatserver.model.donation.food.FoodDonationResponse;
import com.harera.hayatserver.model.donation.medicine.MedicineDonationRequest;
import com.harera.hayatserver.model.donation.medicine.MedicineDonationResponse;
import com.harera.hayatserver.model.donation.property.PropertyDonationRequest;
import com.harera.hayatserver.service.donation.DonationService;

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

    @PostMapping("/foods")
    public ResponseEntity<FoodDonationResponse> donateFood(
                    @RequestPart(name = "body") FoodDonationRequest foodDonationRequest
    //            @RequestPart(value = "image_3", required = false) MultipartFile image3
    ) {
        donationService.donateFood(foodDonationRequest);
        return ResponseEntity.ok(new FoodDonationResponse());
    }

    @PostMapping("/medicine")
    public ResponseEntity<MedicineDonationResponse> donateMedicine(@RequestPart(
                    name = "body") MedicineDonationRequest medicineDonationRequest) {
        MedicineDonationResponse donationResponse =
                        donationService.donateMedicine(medicineDonationRequest);
        return ResponseEntity.ok(donationResponse);
    }
}
