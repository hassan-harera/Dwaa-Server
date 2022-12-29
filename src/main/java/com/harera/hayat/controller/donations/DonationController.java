package com.harera.hayat.controller.donations;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.harera.hayat.model.donation.DonationResponse;
import com.harera.hayat.model.donation.food.FoodDonationRequest;
import com.harera.hayat.model.donation.food.FoodDonationResponse;
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

    @PostMapping("/food")
    public ResponseEntity<FoodDonationResponse> donateFood(
                    @RequestPart(name = "body") FoodDonationRequest foodDonationRequest,
                    @RequestPart(value = "image", required = false) MultipartFile image,
                    @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(
                        donationService.donateFood(foodDonationRequest, image, token));
    }

    @GetMapping("/food")
    public ResponseEntity<List<FoodDonationResponse>> listFoodDonations() {
        List<FoodDonationResponse> foodDonationResponses =
                        donationService.listFoodDonations();
        return ResponseEntity.ok(foodDonationResponses);
    }

}
