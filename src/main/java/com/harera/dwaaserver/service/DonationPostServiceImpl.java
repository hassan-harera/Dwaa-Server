package com.harera.dwaaserver.service;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harera.dwaaserver.dto.request.PostDonationRequest;
import com.harera.dwaaserver.entity.DonationPostEntity;
import com.harera.dwaaserver.mapper.DonationPostEntityMapper;
import com.harera.dwaaserver.repository.DonationPostEntityRepository;

@Service
public class DonationPostServiceImpl implements DonationPostService {

    @Autowired
    private DonationPostEntityRepository donationPostEntityRepository;

    @Override
    public DonationPostEntity insertDonationPost(@Valid @NotNull PostDonationRequest request, Integer uid) {
        DonationPostEntity donationPostEntity = DonationPostEntityMapper.INSTANCE.map(uid, request);
        return donationPostEntityRepository.saveAndFlush(donationPostEntity);
    }
}
