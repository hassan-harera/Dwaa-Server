package com.harera.hayat.controller.donations.food;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.harera.hayat.model.donation.food.FoodDonationRequest;
import com.harera.hayat.model.donation.food.FoodDonationResponse;
import com.harera.hayat.service.donation.food.FoodDonationService;

@RestController
@RequestMapping("/api/v1/donations/food")
public class FoodDonationController {

    private final FoodDonationService foodDonationService;

    public FoodDonationController(FoodDonationService foodDonationService) {
        this.foodDonationService = foodDonationService;
    }

    @PostMapping
    public ResponseEntity<FoodDonationResponse> donateFood(
                    @RequestPart(name = "body") FoodDonationRequest foodDonationRequest,
                    @RequestPart(value = "image", required = false) MultipartFile image,
                    @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(foodDonationService.donateFood(foodDonationRequest,
                        image, token));
    }

    @GetMapping
    public ResponseEntity<List<FoodDonationResponse>> listFoodDonations() {
        List<FoodDonationResponse> foodDonationResponses = foodDonationService.list();
        return ResponseEntity.ok(foodDonationResponses);
    }
}
