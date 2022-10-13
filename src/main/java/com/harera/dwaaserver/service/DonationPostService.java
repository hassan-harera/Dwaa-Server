package com.harera.dwaaserver.service;


import com.harera.dwaaserver.dto.request.PostDonationRequest;
import com.harera.dwaaserver.entity.DonationPostEntity;

import javax.validation.Valid;

public interface DonationPostService {

    DonationPostEntity insertDonationPost(@Valid PostDonationRequest request, Integer uid);
}
