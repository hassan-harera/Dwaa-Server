package com.harera.hayatserver.service;


import javax.validation.Valid;

import com.harera.hayatserver.model.dto.request.PostDonationRequest;
import com.harera.hayatserver.model.entity.DonationPostEntity;

public interface DonationPostService {

    DonationPostEntity insertDonationPost(@Valid PostDonationRequest request, Integer uid);
}
