package com.harera.hayat.service.donation.food;

import java.time.ZonedDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.harera.hayat.exception.EntityNotFoundException;
import com.harera.hayat.model.city.City;
import com.harera.hayat.model.donation.Donation;
import com.harera.hayat.model.donation.DonationCategory;
import com.harera.hayat.model.donation.food.FoodDonation;
import com.harera.hayat.model.donation.food.FoodDonationRequest;
import com.harera.hayat.model.donation.food.FoodDonationResponse;
import com.harera.hayat.model.food.FoodUnit;
import com.harera.hayat.model.user.User;
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

    public FoodDonationService(DonationRepository donationRepository,
                    FoodDonationValidation donationValidation,
                    CityRepository cityRepository, ModelMapper modelMapper,
                    AuthService authService, FoodUnitRepository foodUnitRepository,
                    FoodDonationRepository foodDonationRepository) {
        this.donationRepository = donationRepository;
        this.foodDonationValidation = donationValidation;
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
        this.authService = authService;
        this.foodUnitRepository = foodUnitRepository;
        this.foodDonationRepository = foodDonationRepository;
    }

    public FoodDonationResponse donateFood(FoodDonationRequest foodDonationRequest,
                    MultipartFile image, String token) {
        foodDonationValidation.validateCreate(foodDonationRequest);

        User user = authService.getUserForAuthorization(token);

        City city = cityRepository.findById(foodDonationRequest.getCityId())
                        .orElseThrow(() -> new EntityNotFoundException(City.class,
                                        foodDonationRequest.getCityId()));

        Donation donation = modelMapper.map(foodDonationRequest, Donation.class);
        donation.setCategory(DonationCategory.FOOD);
        donation.setDonationDate(ZonedDateTime.now());
        donation.setCity(city);
        donation.setUser(user);
        Donation savedDonation = donationRepository.save(donation);

        FoodDonation foodDonation =
                        modelMapper.map(foodDonationRequest, FoodDonation.class);

        FoodUnit foodUnit = foodUnitRepository.findById(foodDonationRequest.getUnitId())
                        .orElseThrow(() -> new EntityNotFoundException(FoodUnit.class,
                                        foodDonationRequest.getUnitId()));
        foodDonation.setUnit(foodUnit);

        foodDonation.setDonation(savedDonation);

        FoodDonation saved = foodDonationRepository.save(foodDonation);
        return modelMapper.map(saved, FoodDonationResponse.class);
    }

    public List<FoodDonationResponse> list() {
        return List.of();
    }
}
