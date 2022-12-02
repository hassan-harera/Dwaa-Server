package com.harera.hayatserver.service.donation;

import java.time.ZonedDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.harera.hayatserver.common.exception.EntityNotFoundException;
import com.harera.hayatserver.model.city.City;
import com.harera.hayatserver.model.donation.Donation;
import com.harera.hayatserver.model.donation.DonationCategory;
import com.harera.hayatserver.model.donation.DonationResponse;
import com.harera.hayatserver.model.donation.food.FoodDonation;
import com.harera.hayatserver.model.donation.food.FoodDonationRequest;
import com.harera.hayatserver.model.donation.medicine.Medicine;
import com.harera.hayatserver.model.donation.medicine.MedicineDonation;
import com.harera.hayatserver.model.donation.medicine.MedicineDonationRequest;
import com.harera.hayatserver.model.donation.medicine.MedicineDonationResponse;
import com.harera.hayatserver.model.donation.medicine.MedicineUnit;
import com.harera.hayatserver.model.donation.property.PropertyDonation;
import com.harera.hayatserver.model.donation.property.PropertyDonationRequest;
import com.harera.hayatserver.model.food.FoodUnit;
import com.harera.hayatserver.repository.city.CityRepository;
import com.harera.hayatserver.repository.donation.DonationRepository;
import com.harera.hayatserver.repository.donation.FoodDonationRepository;
import com.harera.hayatserver.repository.donation.medicine.MedicineDonationRepository;
import com.harera.hayatserver.repository.food.FoodUnitRepository;
import com.harera.hayatserver.repository.medicine.MedicineRepository;
import com.harera.hayatserver.repository.medicine.MedicineUnitRepository;

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

    public DonationService(DonationRepository donationRepository,
                    FoodDonationRepository foodDonationRepository,
                    DonationValidation donationValidation, CityRepository cityRepository,
                    MedicineUnitRepository medicineUnitRepository,
                    FoodUnitRepository foodUnitRepository, ModelMapper modelMapper,
                    MedicineDonationRepository medicineDonationRepository,
                    MedicineRepository medicineRepository) {
        this.donationRepository = donationRepository;
        this.foodDonationRepository = foodDonationRepository;
        this.donationValidation = donationValidation;
        this.cityRepository = cityRepository;
        this.medicineUnitRepository = medicineUnitRepository;
        this.foodUnitRepository = foodUnitRepository;
        this.modelMapper = modelMapper;
        this.medicineDonationRepository = medicineDonationRepository;
        this.medicineRepository = medicineRepository;
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

        //TODO : set user
        Donation donation = modelMapper.map(foodDonationRequest, Donation.class);
        donation.setCategory(DonationCategory.FOOD);
        donation.setDonationDate(ZonedDateTime.now());
        donation.setCity(city);
        Donation savedDonation = donationRepository.save(donation);

        foodDonation.setDonation(savedDonation);
        foodDonationRepository.save(foodDonation);
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
}
