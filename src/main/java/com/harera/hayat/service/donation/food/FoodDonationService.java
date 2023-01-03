package com.harera.hayat.service.donation.food;

import java.time.ZonedDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.harera.hayat.exception.EntityNotFoundException;
import com.harera.hayat.model.city.City;
import com.harera.hayat.model.donation.Donation;
import com.harera.hayat.model.donation.DonationCategory;
import com.harera.hayat.model.donation.food.FoodDonation;
import com.harera.hayat.model.donation.food.FoodDonationRequest;
import com.harera.hayat.model.donation.food.FoodDonationResponse;
import com.harera.hayat.model.food.FoodUnit;
import com.harera.hayat.repository.city.CityRepository;
import com.harera.hayat.repository.donation.DonationRepository;
import com.harera.hayat.repository.donation.FoodDonationRepository;
import com.harera.hayat.repository.food.FoodUnitRepository;
import com.harera.hayat.service.user.auth.AuthService;

@Service
public class FoodDonationService {

    private final DonationRepository donationRepository;
    private final FoodDonationValidation foodDonationValidation;
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;
    private final AuthService authService;
    private final FoodUnitRepository foodUnitRepository;
    private final FoodDonationRepository foodDonationRepository;
    private final int foodDonationExpirationDays;

    public FoodDonationService(DonationRepository donationRepository,
                    FoodDonationValidation donationValidation,
                    CityRepository cityRepository, ModelMapper modelMapper,
                    AuthService authService, FoodUnitRepository foodUnitRepository,
                    FoodDonationRepository foodDonationRepository,
                    @Value("${donation.food.expiration_in_days}") int foodDonationExpirationDays) {
        this.donationRepository = donationRepository;
        this.foodDonationValidation = donationValidation;
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
        this.authService = authService;
        this.foodUnitRepository = foodUnitRepository;
        this.foodDonationRepository = foodDonationRepository;
        this.foodDonationExpirationDays = foodDonationExpirationDays;
    }

    public FoodDonationResponse create(FoodDonationRequest foodDonationRequest) {
        foodDonationValidation.validateCreate(foodDonationRequest);

        Donation donation = modelMapper.map(foodDonationRequest, Donation.class);
        donation.setCategory(DonationCategory.FOOD);
        donation.setDonationDate(ZonedDateTime.now());
        donation.setDonationExpirationDate(getDonationExpirationDate());
        donation.setCity(getCity(foodDonationRequest.getCityId()));
        donation.setUser(authService.getRequestUser());
        donationRepository.save(donation);

        FoodDonation foodDonation =
                        modelMapper.map(foodDonationRequest, FoodDonation.class);
        foodDonation.setUnit(getUnit(foodDonationRequest.getUnitId()));
        foodDonation.setDonation(donation);
        foodDonationRepository.save(foodDonation);

        FoodDonationResponse response =
                        modelMapper.map(foodDonation, FoodDonationResponse.class);
        modelMapper.map(donation, response);
        return response;
    }

    private ZonedDateTime getDonationExpirationDate() {
        return ZonedDateTime.now().plusDays(foodDonationExpirationDays);
    }

    private City getCity(Long cityId) {
        return cityRepository.findById(cityId).orElseThrow(
                        () -> new EntityNotFoundException(City.class, cityId));
    }

    private FoodUnit getUnit(Long unitId) {
        return foodUnitRepository.findById(unitId).orElseThrow(
                        () -> new EntityNotFoundException(FoodUnit.class, unitId));

    }

    public List<FoodDonationResponse> list() {
        return List.of();
    }
}
