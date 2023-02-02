package com.harera.hayat.controller.donation.property;

import static org.springframework.http.ResponseEntity.ok;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harera.hayat.model.donation.property.PropertyDonationRequest;
import com.harera.hayat.model.donation.property.PropertyDonationResponse;
import com.harera.hayat.service.donation.property.PropertyDonationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/donations/property")
@Tag(name = "Property-Donation")
public class PropertyDonationController {

    private final PropertyDonationService propertyDonationService;

    public PropertyDonationController(PropertyDonationService donationService) {
        this.propertyDonationService = donationService;
    }

    @PostMapping
    @Operation(summary = "Create", description = "Create property donation",
                    tags = "Property-Donation",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                            description = "success|Ok"),
                            @ApiResponse(responseCode = "400",
                                            description = "error|Bad Request") })
    public ResponseEntity<PropertyDonationResponse> create(
                    @RequestBody PropertyDonationRequest propertyDonationRequest) {
        return ok(propertyDonationService.create(propertyDonationRequest));
    }

    @PostMapping("/{id}")
    @Operation(summary = "Get", description = "Get property donation",
                    tags = "Property-Donation",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                            description = "success|Ok"),
                            @ApiResponse(responseCode = "404",
                                            description = "Not Found") })
    public ResponseEntity<PropertyDonationResponse> get(@PathVariable("id") Long id) {
        return ok(propertyDonationService.get(id));
    }
}