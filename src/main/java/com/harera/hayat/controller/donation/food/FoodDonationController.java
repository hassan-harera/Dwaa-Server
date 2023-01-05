package com.harera.hayat.controller.donation.food;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harera.hayat.model.donation.food.FoodDonationRequest;
import com.harera.hayat.model.donation.food.FoodDonationResponse;
import com.harera.hayat.model.donation.food.FoodDonationUpdateRequest;
import com.harera.hayat.service.donation.food.FoodDonationService;

@RestController
@RequestMapping("/api/v1/donations/food")
public class FoodDonationController {

    private final FoodDonationService foodDonationService;

    public FoodDonationController(FoodDonationService foodDonationService) {
        this.foodDonationService = foodDonationService;
    }

    @PostMapping
    public ResponseEntity<FoodDonationResponse> create(
                    @RequestBody FoodDonationRequest foodDonationRequest) {
        return ResponseEntity.ok(foodDonationService.create(foodDonationRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodDonationResponse> update(@PathVariable("id") Long id,
                    @RequestBody FoodDonationUpdateRequest foodDonationUpdateRequest) {
        return ResponseEntity
                        .ok(foodDonationService.update(id, foodDonationUpdateRequest));
    }

    @GetMapping
    public ResponseEntity<List<FoodDonationResponse>> list() {
        List<FoodDonationResponse> foodDonationResponses = foodDonationService.list();
        return ResponseEntity.ok(foodDonationResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<FoodDonationResponse>> get(@PathVariable("id") Long id) {
        List<FoodDonationResponse> foodDonationResponses = foodDonationService.list();
        return ResponseEntity.ok(foodDonationResponses);
    }
}
