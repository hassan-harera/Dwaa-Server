package com.harera.dwaaserver.service;


import javax.validation.Valid;

import com.harera.dwaaserver.model.dto.request.PostDonationRequest;
import com.harera.dwaaserver.model.entity.DonationPostEntity;

public interface DonationPostService {

    DonationPostEntity insertDonationPost(@Valid PostDonationRequest request, Integer uid);
}
