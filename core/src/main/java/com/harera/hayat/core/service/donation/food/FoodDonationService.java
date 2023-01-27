package com.harera.hayat.core.service.donation.food;

import java.io.File;
import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.harera.core.exception.EntityNotFoundException;
import com.harera.core.exception.FieldFormatException;
import com.harera.core.model.city.City;
import com.harera.core.model.donation.Donation;
import com.harera.core.model.donation.DonationCategory;
import com.harera.core.model.donation.food.FoodDonation;
import com.harera.core.model.donation.food.FoodDonationRequest;
import com.harera.core.model.donation.food.FoodDonationResponse;
import com.harera.core.model.donation.food.FoodDonationUpdateRequest;
import com.harera.core.model.donation.image.DonationImage;
import com.harera.core.model.food.FoodUnit;
import com.harera.core.repository.city.CityRepository;
import com.harera.core.repository.donation.DonationRepository;
import com.harera.core.repository.donation.food.FoodDonationRepository;
import com.harera.core.repository.donation.image.DonationImageRepository;
import com.harera.core.repository.food.FoodUnitRepository;
import com.harera.core.service.user.auth.AuthService;
import com.harera.core.util.ErrorCode;
import com.harera.hayat.core.exception.EntityNotFoundException;
import com.harera.hayat.core.exception.FieldFormatException;
import com.harera.hayat.core.model.city.City;
import com.harera.hayat.core.model.donation.Donation;
import com.harera.hayat.core.model.donation.DonationCategory;
import com.harera.hayat.core.model.donation.food.FoodDonation;
import com.harera.hayat.core.model.donation.food.FoodDonationRequest;
import com.harera.hayat.core.model.donation.food.FoodDonationResponse;
import com.harera.hayat.core.model.donation.food.FoodDonationUpdateRequest;
import com.harera.hayat.core.model.donation.image.DonationImage;
import com.harera.hayat.core.model.food.FoodUnit;
import com.harera.hayat.core.repository.city.CityRepository;
import com.harera.hayat.core.repository.donation.DonationRepository;
import com.harera.hayat.core.repository.donation.food.FoodDonationRepository;
import com.harera.hayat.core.repository.donation.image.DonationImageRepository;
import com.harera.hayat.core.repository.food.FoodUnitRepository;
import com.harera.hayat.core.service.user.auth.AuthService;
import com.harera.hayat.core.util.ErrorCode;
import com.harera.hayat.exception.EntityNotFoundException;
import com.harera.hayat.exception.FieldFormatException;
import com.harera.hayat.model.city.City;
import com.harera.hayat.model.donation.Donation;
import com.harera.hayat.model.donation.DonationCategory;
import com.harera.hayat.model.donation.food.FoodDonation;
import com.harera.hayat.model.donation.food.FoodDonationRequest;
import com.harera.hayat.model.donation.food.FoodDonationResponse;
import com.harera.hayat.model.donation.food.FoodDonationUpdateRequest;
import com.harera.hayat.model.donation.image.DonationImage;
import com.harera.hayat.model.food.FoodUnit;
import com.harera.hayat.repository.city.CityRepository;
import com.harera.hayat.repository.donation.DonationRepository;
import com.harera.hayat.repository.donation.food.FoodDonationRepository;
import com.harera.hayat.repository.donation.image.DonationImageRepository;
import com.harera.hayat.repository.food.FoodUnitRepository;
import com.harera.hayat.service.FileManager;
import com.harera.hayat.service.file.CloudFileService;
import com.harera.hayat.service.user.auth.AuthService;
import com.harera.hayat.util.ErrorCode;

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
    private final FileManager fileManager;
    private final CloudFileService cloudFileService;
    private final DonationImageRepository donationImageRepository;

    public FoodDonationService(DonationRepository donationRepository,
                    FoodDonationValidation donationValidation,
                    CityRepository cityRepository, ModelMapper modelMapper,
                    AuthService authService, FoodUnitRepository foodUnitRepository,
                    FoodDonationRepository foodDonationRepository,
                    @Value("${donation.food.expiration_in_days}") int foodDonationExpirationDays,
                    FileManager fileManager, CloudFileService cloudFileService,
                    DonationImageRepository donationImageRepository) {
        this.donationRepository = donationRepository;
        this.foodDonationValidation = donationValidation;
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
        this.authService = authService;
        this.foodUnitRepository = foodUnitRepository;
        this.foodDonationRepository = foodDonationRepository;
        this.foodDonationExpirationDays = foodDonationExpirationDays;
        this.fileManager = fileManager;
        this.cloudFileService = cloudFileService;
        this.donationImageRepository = donationImageRepository;
    }

    public FoodDonationResponse create(FoodDonationRequest foodDonationRequest) {
        foodDonationValidation.validateCreate(foodDonationRequest);

        Donation donation = modelMapper.map(foodDonationRequest, Donation.class);
        donation.setCategory(DonationCategory.FOOD);
        donation.setDonationDate(OffsetDateTime.now());
        donation.setDonationExpirationDate(getDonationExpirationDate());
        donation.setCity(getCity(foodDonationRequest.getCityId()));
        donation.setUser(authService.getRequestUser());

        FoodDonation foodDonation =
                        modelMapper.map(foodDonationRequest, FoodDonation.class);
        foodDonation.setUnit(getUnit(foodDonationRequest.getUnitId()));
        foodDonation.setDonation(donation);

        donationRepository.save(donation);
        foodDonationRepository.save(foodDonation);

        FoodDonationResponse response =
                        modelMapper.map(donation, FoodDonationResponse.class);
        modelMapper.map(foodDonation, response);
        return response;
    }

    public FoodDonationResponse update(Long id, FoodDonationUpdateRequest request) {
        foodDonationValidation.validateUpdate(id, request);

        FoodDonation foodDonation = foodDonationRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(FoodDonation.class, id));

        Donation donation = foodDonation.getDonation();
        modelMapper.map(request, donation);
        donation.setCity(getCity(request.getCityId()));
        donation.setUser(authService.getRequestUser());
        donationRepository.save(donation);

        modelMapper.map(request, foodDonation);
        foodDonation.setUnit(getUnit(request.getUnitId()));
        foodDonation.setDonation(donation);
        foodDonationRepository.save(foodDonation);

        FoodDonationResponse response =
                        modelMapper.map(donation, FoodDonationResponse.class);
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
                        () -> new EntityNotFoundException(FoodUnit.class, unitId));

    }

    public List<FoodDonationResponse> list(int pageSize, int pageNumber) {
        List<FoodDonation> foodDonationList = foodDonationRepository
                        .findAll(PageRequest.of(pageNumber, pageSize)).getContent();
        List<FoodDonationResponse> response = new LinkedList<>();
        for (FoodDonation foodDonation : foodDonationList) {
            FoodDonationResponse foodDonationResponse = modelMapper
                            .map(foodDonation.getDonation(), FoodDonationResponse.class);
            modelMapper.map(foodDonation, foodDonationResponse);
            response.add(foodDonationResponse);
        }
        return response;
    }

    public FoodDonationResponse get(Long id) {
        FoodDonation foodDonation = foodDonationRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(FoodDonation.class, id));
        FoodDonationResponse response = modelMapper.map(foodDonation.getDonation(),
                        FoodDonationResponse.class);
        modelMapper.map(foodDonation, response);
        return response;
    }

    public FoodDonationResponse insertImage(Long id, MultipartFile image) {
        FoodDonation foodDonation = foodDonationRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(FoodDonation.class, id));
        Donation donation = foodDonation.getDonation();

        File file = fileManager.convert(image);
        if (file == null) {
            throw new FieldFormatException(ErrorCode.FORMAT_DONATION_IMAGE_FILE, "image");
        }

        String fileUrl = cloudFileService.uploadFile(file);
        DonationImage donationImage =
                        new DonationImage(fileUrl, foodDonation.getDonation());
        donationImageRepository.save(donationImage);

        List<DonationImage> images = donation.getImages();
        if (images == null) {
            images = new LinkedList<>();
        }
        images.add(new DonationImage(fileUrl, donation));
        donation.setImages(images);

        return modelMapper.map(foodDonation, FoodDonationResponse.class);
    }

    public void deactivate(Donation donation) {
        FoodDonation foodDonation = foodDonationRepository
                        .findByDonationId(donation.getId()).orElseThrow();
        foodDonation.deactivate();
        foodDonationRepository.save(foodDonation);
    }
}
