package com.harera.dwaaserver.service;


import javax.validation.Valid;

import com.harera.dwaaserver.dto.request.PostDonationRequest;
import com.harera.dwaaserver.entity.DonationPostEntity;

public interface DonationPostService {

    DonationPostEntity insertDonationPost(@Valid PostDonationRequest request, Integer uid);
}
