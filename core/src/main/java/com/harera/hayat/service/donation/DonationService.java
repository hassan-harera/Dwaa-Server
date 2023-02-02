package com.harera.hayat.service.donation;

import java.util.LinkedList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.harera.hayat.model.donation.Donation;
import com.harera.hayat.model.donation.DonationDto;
import com.harera.hayat.model.donation.DonationResponse;
import com.harera.hayat.model.donation.food.FoodDonationResponse;
import com.harera.hayat.repository.city.CityRepository;
import com.harera.hayat.repository.donation.DonationRepository;
import com.harera.hayat.repository.donation.FoodDonationRepository;
import com.harera.hayat.service.donation.food.FoodDonationService;
import com.harera.hayat.service.donation.medicine.MedicineDonationService;

@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final FoodDonationRepository foodDonationRepository;
    private final DonationValidation donationValidation;
    private final FoodDonationService foodDonationService;
    private final MedicineDonationService medicinDonationService;
    private final ModelMapper modelMapper;

    public DonationService(DonationRepository donationRepository,
                    FoodDonationRepository foodDonationRepository,
                    DonationValidation donationValidation, CityRepository cityRepository,
                    FoodDonationService foodDonationService,
                    MedicineDonationService medicinDonationService,
                    ModelMapper modelMapper) {
        this.donationRepository = donationRepository;
        this.foodDonationRepository = foodDonationRepository;
        this.donationValidation = donationValidation;
        this.foodDonationService = foodDonationService;
        this.medicinDonationService = medicinDonationService;
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



    public List<FoodDonationResponse> listFoodDonations() {
        List<FoodDonationResponse> foodDonationList = new LinkedList<>();
        foodDonationRepository.findAll().forEach(foodDonation -> {
            FoodDonationResponse foodDonationResponse =
                            modelMapper.map(foodDonation, FoodDonationResponse.class);
            DonationDto donationDto = modelMapper.map(foodDonation.getDonation(),
                            DonationDto.class);
            modelMapper.map(donationDto, foodDonationResponse);
            foodDonationList.add(foodDonationResponse);
        });
        return foodDonationList;
    }

    public void deactivateExpiredDonations() {

    }
}
