package com.harera.hayat.donation.service.donation.food;

import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.harera.hayat.common.exception.DocumentNotFoundException;
import com.harera.hayat.common.exception.EntityNotFoundException;
import com.harera.hayat.common.model.city.City;
import com.harera.hayat.common.repository.city.CityRepository;
import com.harera.hayat.common.service.auth.JwtService;
import com.harera.hayat.donation.model.donation.Donation;
import com.harera.hayat.donation.model.donation.DonationCategory;
import com.harera.hayat.donation.model.donation.food.FoodDonation;
import com.harera.hayat.donation.model.donation.food.FoodDonationRequest;
import com.harera.hayat.donation.model.donation.food.FoodDonationResponse;
import com.harera.hayat.donation.model.donation.food.FoodDonationUpdateRequest;
import com.harera.hayat.donation.model.food.FoodUnit;
import com.harera.hayat.donation.repository.donation.food.FoodDonationRepository;
import com.harera.hayat.donation.repository.food.FoodUnitRepository;

@Service
public class FoodDonationService {

    private final FoodDonationValidation foodDonationValidation;
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final FoodUnitRepository foodUnitRepository;
    private final FoodDonationRepository foodDonationRepository;
    private final int foodDonationExpirationDays;

    public FoodDonationService(FoodDonationValidation donationValidation,
                    CityRepository cityRepository, ModelMapper modelMapper,
                    JwtService jwtService, FoodUnitRepository foodUnitRepository,
                    FoodDonationRepository foodDonationRepository,
                    @Value("${donation.food.expiration_in_days}") int foodDonationExpirationDays) {
        this.foodDonationValidation = donationValidation;
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
        this.foodUnitRepository = foodUnitRepository;
        this.foodDonationRepository = foodDonationRepository;
        this.foodDonationExpirationDays = foodDonationExpirationDays;
    }

    public FoodDonationResponse create(FoodDonationRequest foodDonationRequest) {
        foodDonationValidation.validateCreate(foodDonationRequest);

        Donation donation = modelMapper.map(foodDonationRequest, Donation.class);
        donation.setCategory(DonationCategory.FOOD);
        donation.setDonationDate(OffsetDateTime.now());
        donation.setDonationExpirationDate(getDonationExpirationDate());
        donation.setCity(getCity(foodDonationRequest.getCityId()));
        donation.setUser(jwtService.getRequestUser());

        FoodDonation foodDonation =
                        modelMapper.map(foodDonationRequest, FoodDonation.class);
        foodDonation.setUnit(getUnit(foodDonationRequest.getUnitId()));
        foodDonationRepository.save(foodDonation);

        FoodDonationResponse response =
                        modelMapper.map(foodDonation, FoodDonationResponse.class);
        modelMapper.map(donation, response);
        return response;
    }

    public FoodDonationResponse update(Long id, FoodDonationUpdateRequest request) {
        foodDonationValidation.validateUpdate(id, request);

        FoodDonation foodDonation = foodDonationRepository.findById(id).orElseThrow(
                        () -> new DocumentNotFoundException(FoodDonation.class, id));

        modelMapper.map(request, foodDonation);
        foodDonation.setCity(getCity(request.getCityId()));
        foodDonation.setUser(jwtService.getRequestUser());

        modelMapper.map(request, foodDonation);
        foodDonation.setUnit(getUnit(request.getUnitId()));
        foodDonationRepository.save(foodDonation);

        FoodDonationResponse response =
                        modelMapper.map(foodDonation, FoodDonationResponse.class);
        modelMapper.map(foodDonation, response);
        response.setId(foodDonation.getId());
        return response;
    }

    private OffsetDateTime getDonationExpirationDate() {
        return OffsetDateTime.now().plusDays(foodDonationExpirationDays);
    }

    private City getCity(Long cityId) {
        return cityRepository.findById(cityId).orElseThrow(
                        () -> new EntityNotFoundException(City.class, cityId));
    }

    private FoodUnit getUnit(Long unitId) {
        return foodUnitRepository.findById(unitId).orElseThrow(
                        () -> new DocumentNotFoundException(FoodUnit.class, unitId));

    }

    public List<FoodDonationResponse> list(int pageSize, int pageNumber) {
        List<FoodDonation> foodDonationList = foodDonationRepository
                        .findAll(PageRequest.of(pageNumber, pageSize)).getContent();
        List<FoodDonationResponse> response = new LinkedList<>();
        for (FoodDonation foodDonation : foodDonationList) {
            FoodDonationResponse foodDonationResponse =
                            modelMapper.map(foodDonation, FoodDonationResponse.class);
            response.add(foodDonationResponse);
        }
        return response;
    }

    public FoodDonationResponse get(Long id) {
        FoodDonation foodDonation = foodDonationRepository.findById(id).orElseThrow(
                        () -> new DocumentNotFoundException(FoodDonation.class, id));
        FoodDonationResponse response =
                        modelMapper.map(foodDonation, FoodDonationResponse.class);
        modelMapper.map(foodDonation, response);
        return response;
    }
}
