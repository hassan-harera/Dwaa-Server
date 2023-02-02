package com.harera.hayat.controller.donation.property;

import static org.springframework.http.ResponseEntity.ok;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harera.hayat.model.donation.property.PropertyDonationRequest;
import com.harera.hayat.model.donation.property.PropertyDonationResponse;
import com.harera.hayat.service.donation.property.PropertyDonationService;

@RestController
@RequestMapping("/api/v1/donations/property")
public class PropertyDonationController {

    private final PropertyDonationService propertyDonationService;

    public PropertyDonationController(PropertyDonationService donationService) {
        this.propertyDonationService = donationService;
    }

    @PostMapping
    public ResponseEntity<PropertyDonationResponse> donateProperty(
                    @RequestBody PropertyDonationRequest propertyDonationRequest) {
        return ok(propertyDonationService.create(propertyDonationRequest));
    }
}
