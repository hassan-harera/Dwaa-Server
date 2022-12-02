package com.harera.hayatserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harera.hayatserver.repository.DonationPostEntityRepository;

@Service
public class DonationPostServiceImpl implements DonationPostService {

    @Autowired
    private DonationPostEntityRepository donationPostEntityRepository;

}
