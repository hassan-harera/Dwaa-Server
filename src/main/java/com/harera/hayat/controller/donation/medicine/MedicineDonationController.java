package com.harera.hayat.controller.donation.medicine;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.harera.hayat.model.donation.medicine.MedicineDonationRequest;
import com.harera.hayat.model.donation.medicine.MedicineDonationResponse;
import com.harera.hayat.service.donation.medicine.MedicineDonationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/v1/donations/medicine")
public class MedicineDonationController {

    private final MedicineDonationService medicineDonationService;

    public MedicineDonationController(MedicineDonationService medicineDonationService) {
        this.medicineDonationService = medicineDonationService;
    }

    @PostMapping
    @Operation(summary = "Create", description = "Create a medicine donations",
                    tags = "Medicine-Donation",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<MedicineDonationResponse> create(
                    @RequestBody MedicineDonationRequest medicineDonationRequest) {
        MedicineDonationResponse donationResponse =
                        medicineDonationService.create(medicineDonationRequest);
        return ResponseEntity.ok(donationResponse);
    }

    @GetMapping
    @Operation(summary = "List", description = "List medicine donations",
                    tags = "Medicine-Donation",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<List<MedicineDonationResponse>> list(
                    @RequestParam(value = "page", defaultValue = "1") int page) {
        List<MedicineDonationResponse> medicineDonationResponses =
                        medicineDonationService.list(page);
        return ResponseEntity.ok(medicineDonationResponses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get", description = "Get a medicine donations",
                    tags = "Medicine-Donation",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<MedicineDonationResponse> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(medicineDonationService.get(id));
    }

    @PostMapping("/{id}/images")
    @Operation(summary = "Insert-Image", description = "Insert a donation image",
                    tags = "Medicine-Donation",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<MedicineDonationResponse> insertImage(
                    @PathVariable("id") Long id,
                    @RequestPart(name = "image") MultipartFile image) {
        return ResponseEntity.ok(medicineDonationService.insertImage(id, image));
    }
}
