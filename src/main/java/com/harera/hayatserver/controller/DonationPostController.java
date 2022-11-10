package com.harera.hayatserver.controller;


import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harera.hayatserver.common.exception.NullDateException;
import com.harera.hayatserver.model.dto.request.PostDonationRequest;
import com.harera.hayatserver.model.entity.DonationPostEntity;
import com.harera.hayatserver.security.JwtService;
import com.harera.hayatserver.service.DonationPostService;

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
