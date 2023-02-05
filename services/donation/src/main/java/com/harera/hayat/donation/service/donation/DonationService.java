package com.harera.hayat.donation.service.donation;

import java.util.LinkedList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.harera.hayat.common.repository.city.CityRepository;
import com.harera.hayat.donation.model.donation.DonationDto;
import com.harera.hayat.donation.model.donation.DonationResponse;
import com.harera.hayat.donation.model.donation.food.FoodDonationResponse;
import com.harera.hayat.donation.repository.donation.food.FoodDonationRepository;
import com.harera.hayat.donation.service.donation.food.FoodDonationService;
import com.harera.hayat.donation.service.donation.medicine.MedicineDonationService;

@Service
public class DonationService {

    private final FoodDonationRepository foodDonationRepository;
    private final DonationValidation donationValidation;
    private final FoodDonationService foodDonationService;
    private final MedicineDonationService medicinDonationService;
    private final ModelMapper modelMapper;

    public DonationService(FoodDonationRepository foodDonationRepository,
                    DonationValidation donationValidation, CityRepository cityRepository,
                    FoodDonationService foodDonationService,
                    MedicineDonationService medicineDonationService,
                    ModelMapper modelMapper) {
        this.foodDonationRepository = foodDonationRepository;
        this.donationValidation = donationValidation;
        this.foodDonationService = foodDonationService;
        this.medicinDonationService = medicineDonationService;
        this.modelMapper = modelMapper;
    }

    public List<DonationResponse> list(Integer page, Integer size, String query,
                    String category) {
        donationValidation.validateList(page, size);
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        List<DonationResponse> donationResponses = new LinkedList<>();
        return donationResponses;
    }

    public List<FoodDonationResponse> listFoodDonations() {
        List<FoodDonationResponse> foodDonationList = new LinkedList<>();
        foodDonationRepository.findAll().forEach(foodDonation -> {
            FoodDonationResponse foodDonationResponse =
                            modelMapper.map(foodDonation, FoodDonationResponse.class);
            DonationDto donationDto = modelMapper.map(foodDonation, DonationDto.class);
            modelMapper.map(donationDto, foodDonationResponse);
            foodDonationList.add(foodDonationResponse);
        });
        return foodDonationList;
    }

    public void deactivateExpiredDonations() {

    }
}
