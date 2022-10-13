package com.harera.dwaaserver.controller;


import com.harera.dwaaserver.common.exception.NullDateException;
import com.harera.dwaaserver.dto.request.PostDonationRequest;
import com.harera.dwaaserver.entity.DonationPostEntity;
import com.harera.dwaaserver.security.JwtService;
import com.harera.dwaaserver.service.DonationPostService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/donation")
public class DonationPostController {

    private final DonationPostService donationPostService;

    private final JwtService jwtService;

    public DonationPostController(DonationPostService donationPostService, JwtService jwtService) {
        this.donationPostService = donationPostService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity insertDonationPost(
            @Valid @RequestBody PostDonationRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        try {
            String username = jwtService.extractUsernameFromAuthorization(authorizationHeader);
            Integer uid = Integer.parseInt(username);
            DonationPostEntity donationPostEntity = donationPostService.insertDonationPost(request, uid);
            return ResponseEntity.ok(donationPostEntity);
        } catch (NullDateException nullDateException) {
            return nullDateException.getResponseEntity();
        } catch (NumberFormatException numberFormatException) {
            return ResponseEntity.badRequest().body("Invalid token");
        }
    }
}
