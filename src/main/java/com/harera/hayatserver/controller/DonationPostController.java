package com.harera.hayatserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harera.hayatserver.security.JwtService;
import com.harera.hayatserver.service.DonationPostService;

@RestController
@RequestMapping("/api/v1/donation")
public class DonationPostController {

    private final DonationPostService donationPostService;
    private final JwtService jwtService;

    public DonationPostController(DonationPostService donationPostService,
                    JwtService jwtService) {
        this.donationPostService = donationPostService;
        this.jwtService = jwtService;
    }
}
