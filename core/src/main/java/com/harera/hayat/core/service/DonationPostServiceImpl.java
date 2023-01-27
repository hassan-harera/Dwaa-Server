package com.harera.hayat.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harera.core.repository.DonationPostEntityRepository;
import com.harera.hayat.core.repository.DonationPostEntityRepository;
import com.harera.hayat.repository.DonationPostEntityRepository;

@Service
public class DonationPostServiceImpl implements DonationPostService {

    @Autowired
    private DonationPostEntityRepository donationPostEntityRepository;

}
