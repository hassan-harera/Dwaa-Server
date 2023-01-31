package com.harera.hayat.controller.donation.food;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.harera.hayat.model.donation.food.FoodDonationRequest;
import com.harera.hayat.model.donation.food.FoodDonationResponse;
import com.harera.hayat.model.donation.food.FoodDonationUpdateRequest;
import com.harera.hayat.service.donation.food.FoodDonationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/donations/food")
@Tag(name = "Food-Donation")
public class FoodDonationController {

    private final FoodDonationService foodDonationService;

    public FoodDonationController(FoodDonationService foodDonationService) {
        this.foodDonationService = foodDonationService;
    }

    @PostMapping
    @Operation(summary = "Create", description = "Create a food donation",
                    tags = "Food-Donation", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<FoodDonationResponse> create(
                    @RequestBody FoodDonationRequest foodDonationRequest) {
        return ResponseEntity.ok(foodDonationService.create(foodDonationRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update", description = "Update a food donation",
                    tags = "Food-Donation", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<FoodDonationResponse> update(@PathVariable("id") Long id,
                    @RequestBody FoodDonationUpdateRequest foodDonationUpdateRequest) {
        return ResponseEntity
                        .ok(foodDonationService.update(id, foodDonationUpdateRequest));
    }

    @GetMapping
    @Operation(summary = "List", description = "List food donations",
                    tags = "Food-Donation", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<List<FoodDonationResponse>> list(
                    @RequestParam(value = "page", defaultValue = "0") int page,
                    @RequestParam(value = "size", defaultValue = "10") int size) {
        List<FoodDonationResponse> foodDonationResponses =
                        foodDonationService.list(size, page);
        return ResponseEntity.ok(foodDonationResponses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get", description = "Get a food donations",
                    tags = "Food-Donation", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<FoodDonationResponse> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(foodDonationService.get(id));
    }
}
