package com.harera.hayatserver.service.donation;

import java.time.ZonedDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.harera.hayatserver.exception.EntityNotFoundException;
import com.harera.hayatserver.model.city.City;
import com.harera.hayatserver.model.donation.Donation;
import com.harera.hayatserver.model.donation.DonationCategory;
import com.harera.hayatserver.model.donation.DonationResponse;
import com.harera.hayatserver.model.donation.food.FoodDonation;
import com.harera.hayatserver.model.donation.food.FoodDonationRequest;
import com.harera.hayatserver.model.donation.property.PropertyDonation;
import com.harera.hayatserver.model.donation.property.PropertyDonationRequest;
import com.harera.hayatserver.model.food.FoodUnit;
import com.harera.hayatserver.repository.city.CityRepository;
import com.harera.hayatserver.repository.donation.DonationRepository;
import com.harera.hayatserver.repository.donation.FoodDonationRepository;
import com.harera.hayatserver.repository.food.FoodUnitRepository;

@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final FoodDonationRepository foodDonationRepository;
    private final DonationValidation donationValidation;
    private final CityRepository cityRepository;
    private final FoodUnitRepository foodUnitRepository;
    private final ModelMapper modelMapper;

    public DonationService(DonationRepository donationRepository,
                    FoodDonationRepository foodDonationRepository,
                    DonationValidation donationValidation, CityRepository cityRepository,
                    FoodUnitRepository foodUnitRepository, ModelMapper modelMapper) {
        this.donationRepository = donationRepository;
        this.foodDonationRepository = foodDonationRepository;
        this.donationValidation = donationValidation;
        this.cityRepository = cityRepository;
        this.foodUnitRepository = foodUnitRepository;
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

        City city = cityRepository.findById(foodDonationRequest.getCityId())
                        .orElseThrow(() -> new EntityNotFoundException(City.class,
                                        foodDonationRequest.getCityId()));

        FoodUnit foodUnit = foodUnitRepository.findById(foodDonationRequest.getUnitId())
                        .orElseThrow(() -> new EntityNotFoundException(FoodUnit.class,
                                        foodDonationRequest.getUnitId()));

        FoodDonation foodDonation =
                        modelMapper.map(foodDonationRequest, FoodDonation.class);
        foodDonation.setUnit(foodUnit);

        Donation donation = modelMapper.map(foodDonationRequest, Donation.class);
        donation.setCategory(DonationCategory.FOOD);
        donation.setDonationDate(ZonedDateTime.now());
        donation.setCity(city);

        Donation save = donationRepository.save(donation);
        //        foodDonation.setDonation(save);
        //        foodDonationRepository.save();
    }
}
