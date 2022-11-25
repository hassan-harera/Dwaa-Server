package com.harera.hayatserver.service.donation;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.harera.hayatserver.model.donation.Donation;
import com.harera.hayatserver.model.donation.DonationResponse;
import com.harera.hayatserver.model.donation.food.FoodDonation;
import com.harera.hayatserver.model.donation.food.FoodDonationRequest;
import com.harera.hayatserver.model.donation.property.PropertyDonation;
import com.harera.hayatserver.model.donation.property.PropertyDonationRequest;
import com.harera.hayatserver.repository.donation.DonationRepository;

@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final DonationValidation donationValidation;
    private final ModelMapper modelMapper;

    public DonationService(DonationRepository donationRepository,
                    DonationValidation donationValidation, ModelMapper modelMapper) {
        this.donationRepository = donationRepository;
        this.donationValidation = donationValidation;
        this.modelMapper = modelMapper;
    }

    public List<DonationResponse> list(Integer page, Integer size, String query,
                    String category) {
        donationValidation.validateList(page, size);
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<Donation> all = donationRepository.findAll(pageable);
        List<DonationResponse> donationResponses = all.map(
                        donation -> modelMapper.map(donation, DonationResponse.class))
                        .toList();
        return donationResponses;
    }

    public ResponseEntity<DonationResponse> donateProperty(
                    PropertyDonationRequest propertyDonationRequest) {
        donationValidation.validateDonateProperty(propertyDonationRequest);
        Donation donation = modelMapper.map(propertyDonationRequest, Donation.class);
        PropertyDonation propertyDonation =
                        modelMapper.map(propertyDonationRequest, PropertyDonation.class);
        Donation save = donationRepository.save(donation);
        DonationResponse donationResponse = modelMapper.map(save, DonationResponse.class);
        return ResponseEntity.ok(donationResponse);
    }

    public void donateFood(FoodDonationRequest foodDonationRequest) {
        donationValidation.validateDonateFood(foodDonationRequest);

        FoodDonation foodDonation =
                        modelMapper.map(foodDonationRequest, FoodDonation.class);
        Donation donation = modelMapper.map(foodDonationRequest, Donation.class);
        donationRepository.save(donation);
    }
}
