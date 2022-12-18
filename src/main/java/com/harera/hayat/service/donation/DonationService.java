package com.harera.hayat.service.donation;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.harera.hayat.common.exception.EntityNotFoundException;
import com.harera.hayat.model.city.City;
import com.harera.hayat.model.donation.Donation;
import com.harera.hayat.model.donation.DonationCategory;
import com.harera.hayat.model.donation.DonationDto;
import com.harera.hayat.model.donation.DonationResponse;
import com.harera.hayat.model.donation.food.FoodDonation;
import com.harera.hayat.model.donation.food.FoodDonationRequest;
import com.harera.hayat.model.donation.food.FoodDonationResponse;
import com.harera.hayat.model.donation.medicine.Medicine;
import com.harera.hayat.model.donation.medicine.MedicineDonation;
import com.harera.hayat.model.donation.medicine.MedicineDonationRequest;
import com.harera.hayat.model.donation.medicine.MedicineDonationResponse;
import com.harera.hayat.model.donation.medicine.MedicineUnit;
import com.harera.hayat.model.donation.property.PropertyDonation;
import com.harera.hayat.model.donation.property.PropertyDonationRequest;
import com.harera.hayat.model.food.FoodUnit;
import com.harera.hayat.model.user.User;
import com.harera.hayat.repository.city.CityRepository;
import com.harera.hayat.repository.donation.DonationRepository;
import com.harera.hayat.repository.donation.FoodDonationRepository;
import com.harera.hayat.repository.donation.medicine.MedicineDonationRepository;
import com.harera.hayat.repository.food.FoodUnitRepository;
import com.harera.hayat.repository.medicine.MedicineRepository;
import com.harera.hayat.repository.medicine.MedicineUnitRepository;
import com.harera.hayat.service.user.auth.AuthService;

@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final FoodDonationRepository foodDonationRepository;
    private final DonationValidation donationValidation;
    private final CityRepository cityRepository;
    private final MedicineUnitRepository medicineUnitRepository;
    private final FoodUnitRepository foodUnitRepository;
    private final ModelMapper modelMapper;
    private final MedicineDonationRepository medicineDonationRepository;
    private final MedicineRepository medicineRepository;
    private final AuthService authService;

    public DonationService(DonationRepository donationRepository,
                    FoodDonationRepository foodDonationRepository,
                    DonationValidation donationValidation, CityRepository cityRepository,
                    MedicineUnitRepository medicineUnitRepository,
                    FoodUnitRepository foodUnitRepository, ModelMapper modelMapper,
                    MedicineDonationRepository medicineDonationRepository,
                    MedicineRepository medicineRepository, AuthService authService) {
        this.donationRepository = donationRepository;
        this.foodDonationRepository = foodDonationRepository;
        this.donationValidation = donationValidation;
        this.cityRepository = cityRepository;
        this.medicineUnitRepository = medicineUnitRepository;
        this.foodUnitRepository = foodUnitRepository;
        this.modelMapper = modelMapper;
        this.medicineDonationRepository = medicineDonationRepository;
        this.medicineRepository = medicineRepository;
        this.authService = authService;
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

    public FoodDonationResponse donateFood(FoodDonationRequest foodDonationRequest,
                    MultipartFile image, String token) {
        donationValidation.validateDonateFood(foodDonationRequest);

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

    public MedicineDonationResponse donateMedicine(
                    MedicineDonationRequest medicineDonationRequest) {
        donationValidation.validateDonateMedicine(medicineDonationRequest);

        Donation donation = modelMapper.map(medicineDonationRequest, Donation.class);
        donation.setCategory(DonationCategory.MEDICINE);
        donation.setCity(getCity(medicineDonationRequest.getCityId()));
        donation.setDonationDate(ZonedDateTime.now());

        Donation savedDonation = donationRepository.save(donation);

        MedicineDonation medicineDonation =
                        modelMapper.map(medicineDonationRequest, MedicineDonation.class);
        medicineDonation.setMedicineUnit(getUnit(medicineDonationRequest.getUnitId()));
        medicineDonation.setDonation(savedDonation);
        medicineDonation.setMedicine(
                        getMedicine(medicineDonationRequest.getMedicineId()));
        MedicineDonation savedMedicine =
                        medicineDonationRepository.save(medicineDonation);
        return modelMapper.map(savedMedicine, MedicineDonationResponse.class);
    }

    private Medicine getMedicine(long medicineId) {
        return medicineRepository.findById(medicineId).orElseThrow(
                        () -> new EntityNotFoundException(Medicine.class, medicineId));
    }

    private MedicineUnit getUnit(long unitId) {
        return medicineUnitRepository.findById(unitId).orElseThrow(
                        () -> new EntityNotFoundException(MedicineUnit.class, unitId));
    }

    private City getCity(long cityId) {
        return cityRepository.findById(cityId).orElseThrow(
                        () -> new EntityNotFoundException(City.class, cityId));
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
}
